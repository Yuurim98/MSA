package com.sparta.msa_exam.product.controller;

import com.sparta.msa_exam.product.common.ApiResponse;
import com.sparta.msa_exam.product.common.exception.CustomException;
import com.sparta.msa_exam.product.common.exception.ErrorCode;
import com.sparta.msa_exam.product.entity.dto.ProductCheckResDto;
import com.sparta.msa_exam.product.entity.dto.ProductReqDto;
import com.sparta.msa_exam.product.entity.dto.ProductResDto;
import com.sparta.msa_exam.product.service.ProductService;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

        // Base64로 인코딩된 값 디코딩
        byte[] decodedBytes = Base64.getDecoder().decode(userNameHeader);
        String decodedUserName = new String(decodedBytes, StandardCharsets.UTF_8);


        log.info("RequestHeader : {}, {}", decodedUserName, userRole);
        if (!userRole.equals("ROLE_MANAGER")) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
        ProductResDto resDto = productService.createProduct(dto, decodedUserName);

        // 응답 헤더에 포트 번호 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Port", serverPort);

        ApiResponse<ProductResDto> response = new ApiResponse<>("success", "상품이 추가되었습니다", resDto,
            null);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(response);

    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<Page<ProductResDto>>> getProductList(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "createAt") String sort,
        @RequestParam(defaultValue = "DESC") Direction direction) {
        Page<ProductResDto> resDtoPage = productService.getProductList(page, size, sort, direction);

        // 응답 헤더에 포트 번호 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Port", serverPort);

        ApiResponse<Page<ProductResDto>> response = new ApiResponse<>("success", "", resDtoPage,
            null);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(response);
    }

    @PostMapping("/check")
    public List<ProductCheckResDto> checkProductsExist(@RequestBody List<Long> productIds) {
        return productService.checkProductsExist(productIds);
    }

}
