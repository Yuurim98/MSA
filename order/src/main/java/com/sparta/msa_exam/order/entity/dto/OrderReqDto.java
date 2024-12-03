package com.sparta.msa_exam.order.entity.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class OrderReqDto {

    private List<Long> productIds;
}
