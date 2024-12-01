package com.sparta.msa_exam.auth.controller;

import com.sparta.msa_exam.auth.common.ApiResponse;
import com.sparta.msa_exam.auth.common.config.jwt.JwtProvider;
import com.sparta.msa_exam.auth.entity.dto.SignInReqDto;
import com.sparta.msa_exam.auth.entity.dto.SignUpReqDto;
import com.sparta.msa_exam.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    @Value("${server.port}")
    private String serverPort;

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<Void>> signUp(@RequestBody SignUpReqDto dto) {
        authService.signUp(dto);
        // 응답 헤더에 포트 번호 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Port", serverPort);

        ApiResponse<Void> response = new ApiResponse<>("success", "회원가입이 완료되었습니다", null, null);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(response);
     }


}
