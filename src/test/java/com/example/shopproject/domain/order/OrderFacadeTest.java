package com.example.shopproject.domain.order;

import com.example.shopproject.domain.item.Item;
import com.example.shopproject.domain.item.ItemRepository.ItemRepository;
import com.example.shopproject.domain.order.request.OrderCreateRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderFacadeTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderFacade orderFacade;

    @AfterEach
    void tearDown(){
        itemRepository.deleteAllInBatch();
    }

    @DisplayName("주문 요청이 mode는 Pessimistic, ThreadNum은 50, Quantity는 2개라면 100개 물품 수량이 감소 한다.")
    @Test
    void orderByThreadNumPessimistic(){
        //given
        String itemCode = String.valueOf(UUID.randomUUID());

        Item item = Item.builder()
                .quantity(300)
                .itemName("참치삼각김밥")
                .price(5000)
                .itemCode(itemCode)
                .build();

        OrderCreateRequest request = OrderCreateRequest.builder()
                .threadNum(50)
                .mode("pessimisticOrderService")
                .itemCount(2)
                .itemCode(itemCode)
                .build();

        itemRepository.save(item);
        //when
        orderFacade.orderByThreadNum(request);
        List<Item> items = itemRepository.findAll();

        //then
        assertThat(items.get(0).getQuantity()).isEqualTo(200);
    }


    @DisplayName("주문 요청이 mode는 Pessimistic, ThreadNum은 50, Quantity는 2개라면 100개 물품 수량이 감소 한다.")
    @Test
    void orderByThreadNumOptimistic(){
        //given
        String itemCode = String.valueOf(UUID.randomUUID());

        Item item = Item.builder()
                .quantity(300)
                .itemName("참치삼각김밥")
                .price(5000)
                .itemCode(itemCode)
                .build();

        OrderCreateRequest request = OrderCreateRequest.builder()
                .threadNum(50)
                .mode("optimisticOrderService")
                .itemCount(2)
                .itemCode(itemCode)
                .build();

        itemRepository.save(item);
        //when
        orderFacade.orderByThreadNum(request);
        List<Item> items = itemRepository.findAll();

        //then
        assertThat(items.get(0).getQuantity()).isEqualTo(200);
    }

}