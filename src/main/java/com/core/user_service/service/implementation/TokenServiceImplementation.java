package com.core.user_service.service.implementation;

import com.core.user_service.api.exception.InvalidTokenException;
import com.core.user_service.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SignatureException;
import java.util.Date;

@Service
@Slf4j
public class TokenServiceImplementation implements TokenService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    public String generateToken(String username) {
        log.info("Generating token for user: {}", username);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .signWith(getSigninKey())
                .compact();
    }

    public void validateToken(String token, String username) {
        try {
            if ((username.equals(extractUsername(token)) && !isTokenExpired(token))) {
                log.info("Token validated for user: {}", username);
            }
        } catch (Exception ex) {
            log.error("Invalid token with message: {},", ex.getMessage());
            throw new InvalidTokenException("The provided token is invalid");
        }
    }

    private Key getSigninKey() {
        return new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    private String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
