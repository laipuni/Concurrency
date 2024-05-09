package com.example.shopproject.domain.order.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCreateRequest {

    private String itemCode;
    private int itemCount;
    private int threadNum;
    private String mode;

    @Builder
    private OrderCreateRequest(final String itemCode, final int itemCount,final int threadNum,final String mode) {
        this.itemCode = itemCode;
        this.itemCount = itemCount;
        this.threadNum = threadNum;
        this.mode = mode;
    }


}
