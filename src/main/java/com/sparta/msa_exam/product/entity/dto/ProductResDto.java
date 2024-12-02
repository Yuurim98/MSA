package com.sparta.msa_exam.product.entity.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sparta.msa_exam.product.entity.Product;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductResDto {

    private Long id;
    private String name;
    private Integer supplyPrice;
    private LocalDateTime createAt;
    private String createdBy;

    public ProductResDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.supplyPrice = product.getSupplyPrice();
        this.createAt = product.getCreateAt();
        this.createdBy = product.getCreatedBy();
    }
}
