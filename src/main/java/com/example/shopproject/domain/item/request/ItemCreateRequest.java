package com.example.shopproject.domain.item.request;

import com.example.shopproject.domain.item.Item;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class ItemCreateRequest {

    @Positive
    private int quantity;

    @Positive
    private int price;

    @NotBlank
    private String itemName;

    @Builder
    private ItemCreateRequest(int quantity, int price, String itemName) {
        this.quantity = quantity;
        this.price = price;
        this.itemName = itemName;
    }

    public Item toEntity(String itemCode){
        return Item.builder()
                .itemName(itemName)
                .price(price)
                .quantity(quantity)
                .itemCode(itemCode)
                .build();
    }
}
