package com.example.shopproject.domain.item.response;

import com.example.shopproject.domain.item.Item;
import lombok.Getter;

@Getter
public class ItemListResponse {

    private Long itemId;
    private String name;
    private int quantity;
    private int price;
    private String itemCode;

    private ItemListResponse(final Long itemId, final String name, final int quantity,final String itemCode,final int price) {
        this.itemId = itemId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.itemCode = itemCode;
    }

    public static ItemListResponse of(Item item){
        return new ItemListResponse(item.getId(), item.getItemName(), item.getQuantity(),item.getItemCode(),item.getPrice());
    }
}
