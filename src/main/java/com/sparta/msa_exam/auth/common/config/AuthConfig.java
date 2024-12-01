package com.sparta.msa_exam.auth.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthConfig {

    private final UserDetailsService userDetailsService;

    // SecurityFilterChain 빈 정의 및 보안 필터 체인을 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CSRF(Cross-Site Request Forgery) 보호를 비활성화
            .csrf(csrf -> csrf.disable())
            // HTTP 요청에 대한 접근 권한을 설정
            .authorizeHttpRequests(authorize -> authorize
                // /auth/signIn 경로에 대해 인증 없이 접근을 허용
                .requestMatchers("/auth/**").permitAll()
                // 그 외의 모든 요청은 인증 필요
                .anyRequest().authenticated()
            )
            // 세션 관리 정책 설정 (세션 사용하지 않음)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        // 설정된 보안 필터 체인을 반환
        return http.build();
    }

    // AuthenticationManager 빈 등록 (AuthenticationManagerBuilder 사용)
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    // PasswordEncoder 빈 등록 (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
