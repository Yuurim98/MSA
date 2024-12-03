package com.sparta.msa_exam.product.service;

import com.sparta.msa_exam.product.common.exception.CustomException;
import com.sparta.msa_exam.product.common.exception.ErrorCode;
import com.sparta.msa_exam.product.entity.Product;
import com.sparta.msa_exam.product.entity.dto.ProductCheckResDto;
import com.sparta.msa_exam.product.entity.dto.ProductReqDto;
import com.sparta.msa_exam.product.entity.dto.ProductResDto;
import com.sparta.msa_exam.product.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
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
        return new ProductResDto(product);
    }

    public Page<ProductResDto> getProductList(int page, int size, String sort, Direction direction) {
        Pageable pageable = PageRequest.of(page, size, direction, sort);

        Page<Product> productPage = productRepository.findAll(pageable);

        return productPage.map(ProductResDto::new);

    }

    public List<ProductCheckResDto> checkProductsExist(List<Long> productIds) {
        return productIds.stream()
            .map(productId -> new ProductCheckResDto(
                productId,
                productRepository.existsById(productId)
            ))
            .toList();
    }
}
