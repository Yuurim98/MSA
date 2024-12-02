package com.sparta.msa_exam.product.entity;

import com.sparta.msa_exam.product.entity.dto.ProductReqDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer supplyPrice;
    private String createdBy;

    public Product(ProductReqDto dto, String userName) {
        this.name = dto.getName();
        this.supplyPrice = dto.getSupplyPrice();
        this.createdBy = userName;
    }

}
