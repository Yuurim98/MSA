package com.sparta.msa_exam.auth.common.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    @Value("${spring.application.name}")
    private String issuer;

    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration;

    private final SecretKey secretKey;

    public JwtProvider(@Value("${service.jwt.secret-key}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    // JWT 생성
    public String crateToken(String userName, String userRole) {
        return Jwts.builder()
            .claim("user_name", userName)
            .claim("user_roles", userRole)
            .issuer(issuer)
            .issuedAt(new Date((System.currentTimeMillis())))
            .expiration(new Date(System.currentTimeMillis() + accessExpiration))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

}
