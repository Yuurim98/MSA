package com.sparta.msa_exam.order.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductCheckResDto {

    private Long productId;
    private boolean exists;

}
