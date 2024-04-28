package com.example.shopproject.domain.order;

import lombok.Getter;

@Getter
public enum OrderStatus {

    INIT("주문생성"),
    CANCLED("주문 취소"),
    PAYMENT_COMPLETE("주문 완료");

    private String description;

    OrderStatus(String description) {
        this.description = description;
    }
}
