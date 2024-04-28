package com.example.shopproject.domain.item.response;

import com.example.shopproject.domain.item.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemCreateResponse {

    private int quantity;
    private int price;
    private String itemName;

    @Builder
    private ItemCreateResponse(int quantity, int price, String itemName) {
        this.quantity = quantity;
        this.price = price;
        this.itemName = itemName;
    }

    public static ItemCreateResponse of(Item item){
        return ItemCreateResponse.builder()
                .itemName(item.getItemName())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .build();
    }

}
