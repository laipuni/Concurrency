package com.example.shopproject.domain.item.response;

import com.example.shopproject.domain.item.Item;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemDetailResponse {

    private Long itemId;
    private String name;
    private int quantity;
    private int price;
    private String itemCode;

    @Builder
    private ItemDetailResponse(final Long itemId, final String name, final int quantity, final int price, final String itemCode) {
        this.itemId = itemId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.itemCode = itemCode;
    }

    public static ItemDetailResponse of(final Item item) {
        return ItemDetailResponse.builder()
                .itemId(item.getId())
                .name(item.getItemName())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .itemCode(item.getItemCode())
                .build();
    }
}
