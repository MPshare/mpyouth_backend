package kr.go.mapo.mpyouth.security.jwt;

import kr.go.mapo.mpyouth.common.ApiException;
import kr.go.mapo.mpyouth.service.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
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

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthenticationToken.class.isAssignableFrom(authentication);
    }
}