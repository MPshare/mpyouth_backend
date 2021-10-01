package kr.go.mapo.mpyouth.security.jwt;


import io.jsonwebtoken.*;
import kr.go.mapo.mpyouth.common.ApiException;
import kr.go.mapo.mpyouth.common.ExceptionEnum;
import kr.go.mapo.mpyouth.security.utils.JwtUtils;
import kr.go.mapo.mpyouth.security.utils.RedisUtils;
import kr.go.mapo.mpyouth.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;

import static  kr.go.mapo.mpyouth.common.ExceptionEnum.*;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private RedisUtils redisUtils;
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = jwtUtils.parseJwt(request);

            if (jwt != null && jwtUtils.isValidateJwtToken(jwt)) {

                String adminLoginId = jwtUtils.getUserNameFromJwtToken(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(adminLoginId);

                if(redisUtils.getData(adminLoginId)==null){
                    throw new CustomJwtException(LOGOUT_USER);
                }

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                 authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
            throw new CustomJwtException(INVALID_JWT_SIGNATURE);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            throw new CustomJwtException(INVALID_JWT);
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            throw new CustomJwtException(EXPIRED_ACCESS_TOKEN);
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
            throw new CustomJwtException(UN_SUPPORTED_JWT);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
            throw new CustomJwtException(NULL_JWT);
        }

        filterChain.doFilter(request, response);
    }
//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        Collection<String> excludeUrlPatterns = new LinkedHashSet<>();
//        excludeUrlPatterns.add("/login/**");
//        excludeUrlPatterns.add("/logout/**");
//        excludeUrlPatterns.add("/signup/**");
//        excludeUrlPatterns.add("/resources/**");
//
//        return excludeUrlPatterns.stream()
//                .anyMatch(pattern -> new AntPathMatcher().match(pattern, request.getServletPath()));
//    }
}
