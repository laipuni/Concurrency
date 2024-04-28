package com.example.shopproject.domain.item;

import com.example.shopproject.domain.IntegrationTestSupport;
import com.example.shopproject.domain.item.request.ItemCreateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.*;


@Slf4j
class ItemServiceTest extends IntegrationTestSupport {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @AfterEach
    void tearDown(){
        itemRepository.deleteAllInBatch();
    }

    @DisplayName("등록할 물품의 정보를 받아 물품을 등록한다.")
    @Test
    void test(){
        //given
        ItemCreateRequest request = ItemCreateRequest.builder()
                .itemName("삼각김밥")
                .price(1700)
                .quantity(30)
                .build();


        //when
        itemService.save(request);
        List<Item> items = itemRepository.findAll();

        //then
        assertThat(items).hasSize(1)
                .extracting("itemName","price","quantity")
                .containsExactly(
                        tuple("삼각김밥",1700,30)
                );

    }

    @DisplayName("주문 받은 수량만큼 현재 수량을 차감한다.")
    @Test
    void removeStock(){
        //given
        int quantity = 10;
        ItemCreateRequest request = ItemCreateRequest.builder()
                .itemName("삼각김밥")
                .price(1700)
                .quantity(30)
                .build();

        itemService.save(request);
        Item item = itemRepository.findAll().get(0);
        //when
        item.reductQuantity(quantity);

        //then
        assertThat(itemRepository.findAll().get(0).getQuantity()).isEqualTo(20);
    }

    @DisplayName("주문 받은 수량이 현재 수량보다 클 경우 에러가 발생한다.")
    @Test
    void removeStockZeroStock(){
        //given
        int quantity = 80;
        ItemCreateRequest request = ItemCreateRequest.builder()
                .itemName("삼각김밥")
                .price(1700)
                .quantity(5)
                .build();
        itemService.save(request);
        Item item = itemRepository.findAll().get(0);

        //when //then
        assertThatThrownBy(() -> item.reductQuantity(quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("재고의 수량이 부족합니다.");
    }

    @DisplayName("동시성문제를 해결하기 위해 Mysql pessimistic Lock을 이용하면 적절하게 상품의 수량이 감소한다.")
    @Test
    void createOrderWithPessimisticLock() throws InterruptedException {
        //given
        int threadNum = 30;
        String itemCode = String.valueOf(UUID.randomUUID());

        Item item = Item.builder()
                .itemCode(itemCode)
                .price(1700)
                .itemName("삼각김밥")
                .quantity(100)
                .build();
        itemRepository.save(item);

        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        CountDownLatch latch = new CountDownLatch(threadNum);

        //when
        for (int i = 0; i < threadNum; i++) {
            executorService.execute(() -> {
                try {
                    itemService.reductQuantityByPessimisticLock(itemCode,1);
                }  finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();
        Item resultItem = itemRepository.findByItemCode(itemCode).get();
        //then
        assertThat(resultItem.getQuantity()).isEqualTo(20);
    }

    @DisplayName("Syncronized방식으로는 스프링의 @Transactional기능 때문에 동시성 문제가 발생해 적절하게 감소하지 않는다.")
    @Test
    void createOrderWithSyncronized() throws InterruptedException {
        //given
        int threadNum = 80;
        String itemCode = String.valueOf(UUID.randomUUID());

        Item item = Item.builder()
                .itemCode(itemCode)
                .price(1700)
                .itemName("삼각김밥")
                .quantity(100)
                .build();
        itemRepository.save(item);

        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        CountDownLatch latch = new CountDownLatch(threadNum);

        //when
        for (int i = 0; i < threadNum; i++) {
            executorService.execute(() -> {
                try {
                    itemService.reductQuantitBySyncronized(itemCode,1);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();
        Item resultItem = itemRepository.findByItemCode(itemCode).get();
        //then
        assertThat(resultItem.getQuantity()).isGreaterThan(20);
    }


}