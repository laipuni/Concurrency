package com.example.shopproject.domain.order.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderCreateRequest {

    @NotNull
    private String itemCode;

    @Positive
    private int itemCount;

    @Positive
    private int threadNum;

    @NotNull
    private String mode;

    @Builder
    private OrderCreateRequest(final String itemCode, final int itemCount,final int threadNum,final String mode) {
        this.itemCode = itemCode;
        this.itemCount = itemCount;
        this.threadNum = threadNum;
        this.mode = mode;
    }


}
