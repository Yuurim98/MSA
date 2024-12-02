package com.sparta.msa_exam.product.controller;

import com.sparta.msa_exam.product.common.ApiResponse;
import com.sparta.msa_exam.product.common.exception.CustomException;
import com.sparta.msa_exam.product.common.exception.ErrorCode;
import com.sparta.msa_exam.product.entity.dto.ProductReqDto;
import com.sparta.msa_exam.product.entity.dto.ProductResDto;
import com.sparta.msa_exam.product.service.ProductService;
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
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    @Value("${server.port}")
    private String serverPort;
    private final ProductService productService;


    @PostMapping("")
    public ResponseEntity<ApiResponse<ProductResDto>> createProduct(
        @RequestBody ProductReqDto dto,
        @RequestHeader("X-User-Name") String userNameHeader,
        @RequestHeader("X-User-Role") String userRole) {

        String userName = new String(Base64.getDecoder().decode(userNameHeader), StandardCharsets.UTF_8);

        log.info("RequestHeader : {}, {}", userName, userRole);
        if(!userRole.equals("ROLE_MANAGER")) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
        ProductResDto resDto = productService.createProduct(dto, userName);

        // 응답 헤더에 포트 번호 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Port", serverPort);

        ApiResponse<ProductResDto> response = new ApiResponse<>("success", "상품이 추가되었습니다", resDto, null);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(response);

    }

}
