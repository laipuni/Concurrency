package com.example.shopproject.domain.order.response;

import com.example.shopproject.domain.order.Order;
import com.example.shopproject.domain.order.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;

import java.time.LocalDate;

public class OrderCreateResponse {

    private int totalPrice;
    private int totalCount;
    private LocalDate orderDate;

    @Builder
    private OrderCreateResponse(final int totalPrice, final int totalCount, final LocalDate orderDate) {
        this.totalPrice = totalPrice;
        this.totalCount = totalCount;
        this.orderDate = orderDate;
    }

    public static OrderCreateResponse of(Order order){
        return OrderCreateResponse.builder()
                .orderDate(order.getOrderDate())
                .totalCount(order.getTotalCount())
                .totalPrice(order.getTotalPrice())
                .build();
    }
}
