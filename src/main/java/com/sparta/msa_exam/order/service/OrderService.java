package com.sparta.msa_exam.order.service;

import com.sparta.msa_exam.order.common.exception.CustomException;
import com.sparta.msa_exam.order.common.exception.ErrorCode;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.dto.OrderReqDto;
import com.sparta.msa_exam.order.entity.dto.ProductCheckResDto;
import com.sparta.msa_exam.order.repository.OrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public Long createOrder(OrderReqDto dto, String userName) {

        validProductIds(dto.getProductIds());

        Order order = orderRepository.save((new Order(userName, dto.getProductIds())));
        return order.getId();
    }



    private void validProductIds(List<Long> productIds) {
        // 상품 유효성
        List<ProductCheckResDto> productChecks = productClient.checkProductsExist(productIds);

        // 유효하지 않은 상품 확인
        List<Long> invalidProductIds =  productChecks.stream()
            .filter(product -> !product.isExists())
            .map(ProductCheckResDto::getProductId)
            .toList();

        if (!invalidProductIds.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_EXIST, invalidProductIds);
        }
    }


}
