package kr.go.mapo.mpyouth.security.utils;


import io.jsonwebtoken.*;
import kr.go.mapo.mpyouth.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Component
@RequiredArgsConstructor
@Service
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.access-expirationMs}") long accessExpirationMs;

    @Value("${jwt.refresh-expirationMs}")
    private long refreshExpirationMs;

    private final RedisUtils redisUtils;




    public String generateAccessToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    public String generateRefreshToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + refreshExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }




    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isValidateJwtToken(String authToken)  {
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
        if (redisUtils.getData(authToken) != null) {
            return false;
        }
        return true;
    }

//    public boolean isValidRefreshToken(String authToken) throws CustomExpiredJwtException {
//        try {
//            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
//            if (redisUtils.getData(authToken) != null) {
//                return false;
//            }
//            return true;
//        } catch (SignatureException e) {
//            logger.error("Invalid JWT signature: {}", e.getMessage());
//            throw new CustomExpiredJwtException(EXPIRED_REFRESH_TOKEN.getMessage());
//        } catch (MalformedJwtException e) {
//            logger.error("Invalid JWT token: {}", e.getMessage());
//            throw new CustomExpiredJwtException(EXPIRED_REFRESH_TOKEN.getMessage());
//        } catch (ExpiredJwtException e) {
//            logger.error("JWT token is expired: {}", e.getMessage());
//            throw new CustomExpiredJwtException(EXPIRED_REFRESH_TOKEN.getMessage());
//        } catch (UnsupportedJwtException e) {
//            logger.error("JWT token is unsupported: {}", e.getMessage());
//            throw new CustomExpiredJwtException(EXPIRED_REFRESH_TOKEN.getMessage());
//        } catch (Exception e) {
//            logger.error("Cannot set user authentication: {}", e);
//            throw new CustomExpiredJwtException(EXPIRED_REFRESH_TOKEN.getMessage());
//        } catch (NullPointerException exception) {
//            System.out.println("Token is null");
//            return false;
//        }
//    }

//    public boolean isValidRefreshToken(String authToken) {
//        try {
//            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
//            if (redisUtils.getData(authToken) != null) {
//                return false;
//            }
//            return true;
//        } catch (ExpiredJwtException exception) {
//            System.out.println("Token Expired UserID : " + exception.getClaims().getSubject());
//            return false;
//        } catch (JwtException exception) {
//            System.out.println("Token Tampered");
//            return false;
//        } catch (NullPointerException exception) {
//            System.out.println("Token is null");
//            return false;
//        }
//    }

    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }
}
