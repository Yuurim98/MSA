package com.sparta.msa_exam.order.entity.dto;

import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderProduct;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderResDto implements Serializable {

    private Long orderId;
    private List<Long> productIds;

    public static OrderResDto fromDto(Order order) {
        List<Long> productIds = order.getOrderProducts().stream()
            .map(OrderProduct::getProductId)
            .collect(Collectors.toList());
        return new OrderResDto(order.getId(), productIds);
    }

}
