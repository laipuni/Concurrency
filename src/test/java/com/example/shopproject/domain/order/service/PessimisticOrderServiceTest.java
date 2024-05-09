package com.example.shopproject.domain.order.service;

import com.example.shopproject.domain.item.Item;
import com.example.shopproject.domain.item.ItemRepository.ItemRepository;
import com.example.shopproject.domain.order.OrderRepository;
import com.example.shopproject.domain.order.response.OrderCreateResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class PessimisticOrderServiceTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PessimisticOrderService orderService;

    @Autowired
    ItemRepository itemRepository;

    @DisplayName("주문할 상품의 코드와 수량을 입력받아 주문을 생성한다.")
    @Test
    void orderBy(){
        //given
        String itemCode = String.valueOf(UUID.randomUUID());
        int orderItemQuantity = 20;

        Item item = Item.builder()
                .quantity(50)
                .itemName("참치삼각김밥")
                .price(5000)
                .itemCode(itemCode)
                .build();

        itemRepository.save(item);

        //when
        OrderCreateResponse orderCreateResponse = orderService.orderBy(itemCode, orderItemQuantity);
        List<Item> items = itemRepository.findAll();

        //then
        assertThat(items.get(0).getQuantity()).isEqualTo(30);
        assertThat(orderCreateResponse)
                .extracting("totalPrice","totalCount")
                .containsExactly(100000,20);
    }


}