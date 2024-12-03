package com.sparta.msa_exam.order.controller;

import com.sparta.msa_exam.order.common.ApiResponse;
import com.sparta.msa_exam.order.entity.dto.OrderReqDto;
import com.sparta.msa_exam.order.service.OrderService;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    @Value("${server.port}")
    private String serverPort;
    private final OrderService orderService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<Long>> createOrder(
        @RequestBody OrderReqDto dto,
        @RequestHeader("X-User-Name") String userNameHeader) {

        // Base64로 인코딩된 값 디코딩
        byte[] decodedBytes = Base64.getDecoder().decode(userNameHeader);
        String decodedUserName = new String(decodedBytes, StandardCharsets.UTF_8);

        log.info("RequestHeader : {}", decodedUserName);
        orderService.createOrder(dto, decodedUserName);

        // 응답 헤더에 포트 번호 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Port", serverPort);

        ApiResponse<Long> response = new ApiResponse<>("success", "주문 처리되었습니다", orderService.createOrder(dto, decodedUserName),
            null);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(response);

    }

}
