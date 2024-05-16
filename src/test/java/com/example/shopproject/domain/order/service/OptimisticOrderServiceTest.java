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
class OptimisticOrderServiceTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OptimisticOrderService optimisticOrderService;

    @Autowired
    ItemRepository itemRepository;
    @DisplayName("주문할 상품의 코드와 수량을 입력받아 주문을 생성한다")
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
        OrderCreateResponse orderCreateResponse = optimisticOrderService.orderBy(itemCode, orderItemQuantity,1);
        List<Item> items = itemRepository.findAll();

        //then
        assertThat(items.get(0).getQuantity()).isEqualTo(30);
        assertThat(orderCreateResponse)
                .extracting("totalPrice","totalCount")
                .containsExactly(100000,20);
    }

    @DisplayName("100명의 사용자가 한가지 상품을 1개씩 주문하면 물품의 수량이 100개 차감된다.")
    @Test
    void test() throws InterruptedException {
        //given
        int thread = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(thread);
        CountDownLatch latch = new CountDownLatch(thread);
        String itemCode = String.valueOf(UUID.randomUUID());

        Item item = Item.builder()
                .quantity(200)
                .itemName("참치삼각김밥")
                .price(5000)
                .itemCode(itemCode)
                .build();

        itemRepository.save(item);
        //when
        for (int i = 0; i < thread; i++) {
            final int threadNum = i;
            executorService.execute(() ->{
                try {
                    optimisticOrderService.orderBy(itemCode,1,threadNum);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();
        List<Item> items = itemRepository.findAll();
        //then
        Assertions.assertThat(items.get(0).getQuantity()).isEqualTo(100);
    }


}