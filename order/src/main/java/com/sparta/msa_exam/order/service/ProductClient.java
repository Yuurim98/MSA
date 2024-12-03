package com.sparta.msa_exam.order.service;

import com.sparta.msa_exam.order.entity.dto.ProductCheckResDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product")
public interface ProductClient {

    @PostMapping("/products/check")
    List<ProductCheckResDto> checkProductsExist(@RequestBody List<Long> productIds);
}
