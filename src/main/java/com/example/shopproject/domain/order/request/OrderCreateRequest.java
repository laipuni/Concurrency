package com.example.shopproject.domain.order.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCreateRequest {

    private String itemCode;
    private int itemCount;

    @Builder
    private OrderCreateRequest(final String itemCode, final int itemCount) {
        this.itemCode = itemCode;
        this.itemCount = itemCount;
    }


}
