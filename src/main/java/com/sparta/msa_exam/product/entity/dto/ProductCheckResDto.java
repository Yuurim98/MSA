package com.sparta.msa_exam.product.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductCheckResDto {

    private Long productId;
    private boolean exists;

}
