package com.example.shopproject.domain.item;

import com.example.shopproject.domain.IntegrationTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
class ItemRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private ItemRepository itemRepository;

    @DisplayName("물품의 코드와 동일한 물품을 조회 한다.")
    @Test
    void findByItemCode(){
        //given
        String itemCode = String.valueOf(UUID.randomUUID());
        Item item = Item.builder()
                .itemCode(itemCode)
                .price(1700)
                .itemName("삼각김밥")
                .quantity(30)
                .build();
        itemRepository.save(item);

        //when
        Item findItem = itemRepository.findByItemCode(itemCode).get();

        //then
        assertThat(findItem.getItemCode()).isEqualTo(itemCode);
    }

    @DisplayName("물품의 코드와 동일한 물품을 조회 한다.")
    @Test
    void findByItemCodeForUpdate(){
        //given
        String itemCode = String.valueOf(UUID.randomUUID());
        Item item = Item.builder()
                .itemCode(itemCode)
                .price(1700)
                .itemName("삼각김밥")
                .quantity(30)
                .build();
        itemRepository.save(item);

        //when
        Item findItem = itemRepository.findByItemCode(itemCode).get();

        //then
        assertThat(findItem.getItemCode()).isEqualTo(itemCode);
    }

}