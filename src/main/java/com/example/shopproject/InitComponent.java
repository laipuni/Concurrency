package com.example.shopproject;

import com.example.shopproject.domain.item.Item;
import com.example.shopproject.domain.item.ItemRepository.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InitComponent {

    private final ItemRepository itemRepository;

    @PostConstruct
    public void init(){
        String itemCode1 = String.valueOf(UUID.randomUUID());
        String itemCode2 = String.valueOf(UUID.randomUUID());

        Item item1 = Item.builder()
                .itemCode(itemCode1)
                .itemName("삼각김밥")
                .price(1500)
                .quantity(100)
                .build();

        Item item2 = Item.builder()
                .itemCode(itemCode2)
                .itemName("푸딩")
                .price(3500)
                .quantity(200)
                .build();

        itemRepository.saveAll(List.of(item1,item2));
    }


}
