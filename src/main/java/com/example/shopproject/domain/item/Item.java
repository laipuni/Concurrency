package com.example.shopproject.domain.item;

import com.example.shopproject.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Item extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private String itemCode;

    @Version
    private int version;

    @Builder
    private Item(final int quantity, final int price, String itemName,final String itemCode) {
        this.quantity = quantity;
        this.price = price;
        this.itemName = itemName;
        this.itemCode = itemCode;
    }

    public void reductQuantity(final int quantity){
        if(this.quantity < quantity){
            throw new IllegalArgumentException("재고의 수량이 부족합니다.");
        }

        this.quantity -= quantity;
    }

}
