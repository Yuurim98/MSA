package com.sparta.msa_exam.gateway.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Slf4j
@Component
public class JwtProvider {

    @Value("${service.jwt.secret-key}")
    private String  secretKey;

    public String extractToken(ServerWebExchange exchange) {
        // Authorization 헤더의 값 추출
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        // null이 아니고 "Bearer "로 시작한다면
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // "Bearer "를 제외한 실제 토큰 부분을 반환
        }

        // null이거나 형식이 맞지 않으면 null 반환
        return null;
    }

    public Claims validateToken(String token) {

        try {
            // SecretKey 생성 및 JWT 서명 검증
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
            Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(key)
                .build().parseSignedClaims(token);
            log.info("##payload {}", claimsJws.getPayload().toString());

            return claimsJws.getPayload();
        } catch (Exception e) {
            log.error("유효하지 않은 토큰 {}", token);
            return null;
        }
    }



}
