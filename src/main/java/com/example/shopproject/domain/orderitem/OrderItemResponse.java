package com.example.shopproject.domain.orderitem;

import com.example.shopproject.domain.order.OrderType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderItemResponse {

    private Long orderId;
    private int threadNum;
    private int quantity;
    private OrderType orderType;

    @Builder
    private OrderItemResponse(final Long orderId, final int threadNum, final int quantity, final OrderType orderType) {
        this.orderId = orderId;
        this.threadNum = threadNum;
        this.quantity = quantity;
        this.orderType = orderType;
    }

    public static OrderItemResponse of(final OrderItem orderItem){
        return OrderItemResponse.builder()
                .orderId(orderItem.getId())
                .threadNum(orderItem.getOrder().getThreadNum())
                .quantity(orderItem.getCount())
                .orderType(orderItem.getOrder().getOrderType())
                .build();
    }
}
