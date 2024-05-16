package com.example.shopproject.domain.order;

import com.example.shopproject.domain.IntegrationTestSupport;
import com.example.shopproject.domain.item.Item;
import com.example.shopproject.domain.orderitem.OrderItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest extends IntegrationTestSupport{

    @DisplayName("주문할 물품들과 유저의 정보를 받아 주문을 생성한다.")
    @Test
    void createOrder(){
        //given
        Item item = Item.builder()
                .quantity(20)
                .itemName("삼각김밥")
                .price(1700)
                .build();

        List<OrderItem> orderItems = List.of(
                OrderItem.builder()
                        .count(5)
                        .itemPrice(1700)
                        .item(item)
                        .build()
        );

        //when
        Order order = Order.createBy(orderItems,1,OrderType.PESSIMISTIC);

        //then
        assertThat(order)
                .extracting("totalPrice","totalCount")
                .containsExactly(8500,5);
    }

    @DisplayName("주문할 물품들의 총 계수를 계산해서 주문 정보에 저장한다.")
    @Test
    void computeTotalCount(){
        //given

        //when

        //then
    }

    @DisplayName("주문할 물품들의 가격을 더해서 총가격을 주문정보에 저장한다.")
    @Test
    void computeTotalPrice(){
        //given

        //when

        //then
    }

}