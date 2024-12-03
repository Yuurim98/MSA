package com.sparta.msa_exam.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    private String orderedBy;

    public Order(String orderedBy, List<Long> productIds) {
        this.orderedBy = orderedBy;

        // productIds로 OrderProduct 엔티티 생성 및 관계 설정
        this.orderProducts = productIds.stream()
            .map(productId -> new OrderProduct(this, productId))
            .toList();
    }

}
