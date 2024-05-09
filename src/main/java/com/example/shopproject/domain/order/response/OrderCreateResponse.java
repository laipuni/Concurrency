package com.example.shopproject.domain.order.response;

import com.example.shopproject.domain.order.Order;
import lombok.Builder;

import java.time.LocalDate;

public class OrderCreateResponse {

    private int totalPrice;
    private int totalCount;

    @Builder
    private OrderCreateResponse(final int totalPrice, final int totalCount) {
        this.totalPrice = totalPrice;
        this.totalCount = totalCount;
    }

    public static OrderCreateResponse of(Order order){
        return OrderCreateResponse.builder()
                .totalCount(order.getTotalCount())
                .totalPrice(order.getTotalPrice())
                .build();
    }
}
