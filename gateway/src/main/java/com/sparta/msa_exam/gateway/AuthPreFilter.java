package com.sparta.msa_exam.gateway;

import com.sparta.msa_exam.gateway.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthPreFilter implements GlobalFilter, Ordered {

    private final JwtProvider jwtProvider;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        log.info("###authFilter {}", path);

        if (path.startsWith("/auth/")) {
            log.info("auth 경로 : 인증 제외");
            return chain.filter(exchange);
        }

        String token = jwtProvider.extractToken(exchange);
        Claims claims = jwtProvider.validateToken(token);

        // 추출한 토큰이 null 또는 유효하지 않으면 상태 코드를 설정하고 요청 처리를 종료한다
        if (token == null || claims == null) {
            log.info("로그인 X");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String encodedUserName = claims.get("user_name", String.class);
        String userRole = claims.get("user_roles", String.class);


        return chain.filter(addHeaders(exchange, encodedUserName, userRole));

    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    private ServerWebExchange addHeaders(ServerWebExchange exchange, String encodedUserName, String userRole) {

        return exchange.mutate()
            .request(exchange.getRequest().mutate()
                .header("X-User-Name", encodedUserName)
                .header("X-User-Role", userRole)
                .build())
            .build();
    }

}
