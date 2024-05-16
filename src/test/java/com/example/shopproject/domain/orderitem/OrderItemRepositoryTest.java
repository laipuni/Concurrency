package com.example.shopproject.domain.orderitem;

import com.example.shopproject.domain.item.Item;
import com.example.shopproject.domain.item.ItemRepository.ItemRepository;
import com.example.shopproject.domain.order.Order;
import com.example.shopproject.domain.order.OrderRepository;
import com.example.shopproject.domain.order.OrderType;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderItemRepositoryTest {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderRepository orderRepository;


    @DisplayName("itemCode을 가진 Item을 주문한 OrderItem을 조회한다.")
    @Test
    void findAllByItemCode(){
        //given
        String itemCode = String.valueOf(UUID.randomUUID());
        Item item = Item.builder()
                .itemName("달걀샌드위치")
                .quantity(200)
                .price(3500)
                .itemCode(itemCode)
                .build();

        OrderItem orderItem = OrderItem.create(item,10);

        Order order = Order.createBy(List.of(orderItem),1, OrderType.PESSIMISTIC);

        itemRepository.save(item);
        orderItemRepository.save(orderItem);
        orderRepository.save(order);

        //when
        List<OrderItem> orderItems = orderItemRepository.findAllByItemCodeDesc(itemCode);

        //then
        Assertions.assertThat(orderItems).hasSize(1)
                .extracting("count","itemPrice","itemCode")
                .containsExactlyInAnyOrder(tuple(10,3500,itemCode));
    }

}