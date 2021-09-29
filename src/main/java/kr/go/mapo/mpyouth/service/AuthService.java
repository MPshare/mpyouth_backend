package kr.go.mapo.mpyouth.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import kr.go.mapo.mpyouth.common.ApiException;
import kr.go.mapo.mpyouth.domain.*;
import kr.go.mapo.mpyouth.payload.request.*;
import kr.go.mapo.mpyouth.payload.response.LoginResponse;
import kr.go.mapo.mpyouth.payload.response.TokenResponse;
import kr.go.mapo.mpyouth.repository.OrganizationRepository;
import kr.go.mapo.mpyouth.repository.RoleRepository;
import kr.go.mapo.mpyouth.repository.UserRepository;
import kr.go.mapo.mpyouth.security.jwt.AuthTokenFilter;
import kr.go.mapo.mpyouth.security.jwt.CustomAuthenticationToken;
import kr.go.mapo.mpyouth.security.jwt.CustomJwtException;
import kr.go.mapo.mpyouth.security.utils.JwtUtils;
import kr.go.mapo.mpyouth.security.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static  kr.go.mapo.mpyouth.common.ExceptionEnum.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;
    private final RedisUtils redisUtils;

    private final UserDetailsServiceImpl userDetailsService;

    @Value("${jwt.refresh-expirationMs}")
    private long refreshExpirationMs;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Transactional
    public void signup(SignupRequest signUpRequest) {

        if (userRepository.existsByAdminLoginId(signUpRequest.getAdminLoginId())) {
            throw new ApiException(ALREADY_REGISTERED_USER);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ApiException(ALREADY_REGISTERED_EMAIL);
        }

        Organization organization = organizationRepository.findById(signUpRequest.getOrganizationId())
                .orElseThrow(() ->
                        new ApiException(NOT_FOUND_ORGANIZATION_WITH_ORGANIZATION_ID));

        User user = User.builder()
                .adminLoginId(signUpRequest.getAdminLoginId())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .organization(organization)
                .phone(signUpRequest.getPhone())
                .build();


        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_MANAGER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;


                    default:
                        Role managerRole = roleRepository.findByName(ERole.ROLE_MANAGER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        user.setRoles(roles);
                        userRepository.save(user);
                        roles.add(managerRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

//        return strRoles + " 등록 성공";
    }


    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new CustomAuthenticationToken(loginRequest.getAdminLogId(), loginRequest.getPassword(), new ArrayList<>()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.generateAccessToken(authentication);
        String refreshToken = jwtUtils.generateRefreshToken(authentication);

        redisUtils.setDataExpire(loginRequest.getAdminLogId(), refreshToken, refreshExpirationMs);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new LoginResponse(accessToken,
                refreshToken,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);

    }


    @Transactional
    public void logout(HttpServletRequest request) {
        String jwt = jwtUtils.parseJwt(request);
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        redisUtils.deleteData(username);
    }

    public TokenResponse reIssueToken(TokenRequest tokenRequest) throws CustomJwtException {
        try {
            String refreshToken = tokenRequest.getRefreshToken();

            jwtUtils.isValidateJwtToken(refreshToken);
            String adminLoginId = jwtUtils.getUserNameFromJwtToken(refreshToken);

            if (redisUtils.getData(adminLoginId) == null || redisUtils.getData(adminLoginId).equals(refreshToken)==false) {
                throw new CustomJwtException(EXPIRED_REFRESH_TOKEN);
            }

            UserDetails user = userDetailsService.loadUserByUsername(adminLoginId);
            CustomAuthenticationToken authentication = new CustomAuthenticationToken(user,null,user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String newAccessToken = jwtUtils.generateAccessToken(authentication);
            String newRefreshToken = jwtUtils.generateRefreshToken(authentication);

            redisUtils.setDataExpire(adminLoginId, newRefreshToken, refreshExpirationMs);
            return new TokenResponse(newAccessToken, newRefreshToken);

        }catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
            throw new CustomJwtException(INVALID_JWT_SIGNATURE);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            throw new CustomJwtException(INVALID_JWT);
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            throw new CustomJwtException(EXPIRED_REFRESH_TOKEN);
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
            throw new CustomJwtException(UN_SUPPORTED_JWT);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
            throw new CustomJwtException(NULL_JWT);
        }
    }
}
