package kr.go.mapo.mpyouth.service;

import kr.go.mapo.mpyouth.common.ApiException;
import kr.go.mapo.mpyouth.domain.*;
import kr.go.mapo.mpyouth.payload.request.*;
import kr.go.mapo.mpyouth.payload.response.JwtResponse;
import kr.go.mapo.mpyouth.repository.AuthEmailRepository;
import kr.go.mapo.mpyouth.repository.OrganizationRepository;
import kr.go.mapo.mpyouth.repository.RoleRepository;
import kr.go.mapo.mpyouth.repository.UserRepository;
import kr.go.mapo.mpyouth.security.jwt.CustomAuthenticationToken;
import kr.go.mapo.mpyouth.security.utils.JwtUtils;
import kr.go.mapo.mpyouth.security.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
    private final AuthEmailRepository authEmailRepository;

    private final JavaMailSender mailSender;
    @Value("${jwt.refresh-expirationMs}")
    private long refreshExpirationMs;


    @Transactional
    public String signup(SignupRequest signUpRequest){

        if (userRepository.existsByAdminLoginId(signUpRequest.getAdminLoginId())) {
            throw new ApiException(ALREADY_REGISTERED_USER);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ApiException(ALREADY_REGISTERED_EMAIL);
        }

        Organization organization =organizationRepository.findById(signUpRequest.getOrganizationId())
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

        return strRoles+" 등록 성공";
    }


    @Transactional
    public JwtResponse login(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new CustomAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword(),new ArrayList<>()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.generateAccessToken(authentication);
        String refreshToken = jwtUtils.generateRefreshToken(authentication);

        redisUtils.setDataExpire(loginRequest.getUsername(),refreshToken, refreshExpirationMs);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(accessToken,
                refreshToken,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);

    }



    @Transactional
    public void logout(HttpServletRequest request){
        String jwt = jwtUtils.parseJwt(request);
        String username=jwtUtils.getUserNameFromJwtToken(jwt);
        redisUtils.deleteData(username);
    }




//    @Transactional
//    public TokenDto reissue(TokenRequest tokenRequest) {
//        // 1. Refresh Token 검증
//        if (!jwtUtils.validateJwtToken(tokenRequest.getRefreshToken())) {
//            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
//        }
//
//        // 2. Access Token 에서 Member ID 가져오기
//        Authentication authentication = jwtUtils.get
//
//        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
//        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
//                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));
//
//        // 4. Refresh Token 일치하는지 검사
//        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
//            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
//        }
//
//        // 5. 새로운 토큰 생성
//        JwtResponse tokenDto = jwtUtils.generateAceessToken(authentication);
//
//        // 6. 저장소 정보 업데이트
//        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
//        refreshTokenRepository.save(newRefreshToken);
//
//        // 토큰 발급
//        return tokenDto;
//    }
}