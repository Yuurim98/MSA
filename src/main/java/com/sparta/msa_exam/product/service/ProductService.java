package com.sparta.msa_exam.product.service;

import com.sparta.msa_exam.product.common.exception.CustomException;
import com.sparta.msa_exam.product.common.exception.ErrorCode;
import com.sparta.msa_exam.product.entity.Product;
import com.sparta.msa_exam.product.entity.dto.ProductReqDto;
import com.sparta.msa_exam.product.entity.dto.ProductResDto;
import com.sparta.msa_exam.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResDto createProduct(ProductReqDto dto, String userName) {
        if (productRepository.findByName(dto.getName()).isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_EXISTS);
        }

        Product product = new Product(dto, userName);

        productRepository.save(product);
        return new ProductResDto(
            product.getId(),
            product.getName(),
            product.getSupplyPrice(),
            product.getCreatedBy()
        );
    }

}
