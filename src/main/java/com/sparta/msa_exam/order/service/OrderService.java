package com.sparta.msa_exam.order.service;

import com.sparta.msa_exam.order.common.exception.CustomException;
import com.sparta.msa_exam.order.common.exception.ErrorCode;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderProduct;
import com.sparta.msa_exam.order.entity.dto.OrderReqDto;
import com.sparta.msa_exam.order.entity.dto.OrderResDto;
import com.sparta.msa_exam.order.entity.dto.ProductCheckResDto;
import com.sparta.msa_exam.order.repository.OrderRepository;
import java.util.List;
import java.util.stream.Collectors;
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

    public Long updateOrder(Long id, OrderReqDto dto, String decodedUserName) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_EXIST));

        if (!order.getOrderedBy().equals(decodedUserName)) {
            throw new CustomException(ErrorCode.ORDER_NOT_MATCH_USER);
        }

        validProductIds(dto.getProductIds());

        List<OrderProduct> newOrderProducts = dto.getProductIds().stream()
            .map(productId -> new OrderProduct(order, productId)) // 새로운 OrderProduct 생성
            .collect(Collectors.toList());

        // 기존 주문의 상품 리스트에 새로 추가된 상품들을 합치기
        log.info("기존 주문 : {}", order.getOrderProducts());
        order.getOrderProducts().addAll(newOrderProducts);
        log.info("추가 주문 : {}", order.getOrderProducts());

        orderRepository.save(order);
        return order.getId();
    }

    public OrderResDto getOder(Long id, String decodedUserName) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_EXIST));

        if (!order.getOrderedBy().equals(decodedUserName)) {
            throw new CustomException(ErrorCode.ORDER_NOT_MATCH_USER);
        }
        return OrderResDto.fromDto(order);
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
