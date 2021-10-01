package kr.go.mapo.mpyouth.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import kr.go.mapo.mpyouth.common.ApiException;
import kr.go.mapo.mpyouth.service.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static  kr.go.mapo.mpyouth.common.ExceptionEnum.*;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String adminLoginId = authentication.getName();
        String password = (String) authentication.getCredentials();

        UserDetails user = userDetailsService.loadUserByUsername(adminLoginId);

        if (user == null) {
            throw new ApiException(NOT_FOUND_USER);
        }

        if (!this.passwordEncoder.matches(password, user.getPassword())) {
            throw new ApiException(BAD_CREDENTIAL_ERROR);
        }

        CustomAuthenticationToken result = new CustomAuthenticationToken(user,null,user.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }

//    public Authentication authenticate_token(String accessToken) {
//
//        UserDetails user = userDetailsService.loadUserByUsername(adminLoginId);
//
//        if (user == null) {
//            throw new ApiException(NOT_FOUND_USER);
//        }
//
//        CustomAuthenticationToken result = new CustomAuthenticationToken(user,null,user.getAuthorities());
//        result.setDetails(authentication.getDetails());
//        return result;
//    }

//    public Authentication getAuthentication(String accessToken) {
//        // 토큰 복호화
//        Claims claims = parseClaims(accessToken);
//
//        if (claims.get(AUTHORITIES_KEY) == null) {
//            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
//        }
//
//        // 클레임에서 권한 정보 가져오기
//        Collection<? extends GrantedAuthority> authorities =
//                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
//                        .map(SimpleGrantedAuthority::new)
//                        .collect(Collectors.toList());
//
//        // UserDetails 객체를 만들어서 Authentication 리턴
//        UserDetails principal = new User(claims.getSubject(), "", authorities);
//
//        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
//    }
//

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthenticationToken.class.isAssignableFrom(authentication);
    }


}