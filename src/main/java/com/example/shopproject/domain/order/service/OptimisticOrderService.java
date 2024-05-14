package com.example.shopproject.domain.order.service;

import com.example.shopproject.domain.item.Item;
import com.example.shopproject.domain.item.ItemRepository.ItemRepository;
import com.example.shopproject.domain.order.Order;
import com.example.shopproject.domain.order.OrderRepository;
import com.example.shopproject.domain.order.response.OrderCreateResponse;
import com.example.shopproject.domain.orderitem.OrderItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OptimisticOrderService implements OrderService{

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    @OptimisticRetry
    @Transactional
    @Override
    public OrderCreateResponse orderBy(final String itemCode, final int Quantity) {
        //충돌 발생시 ObjectOptimisticLockingFailureException 발생
        Item item = itemRepository.findByItemCodeWithVersion(itemCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 물품은 존재하지 않습니다."));
        OrderItem orderItem = OrderItem.create(item,Quantity);
        Order order = Order.createBy(List.of(orderItem));
        orderRepository.save(order);
        return OrderCreateResponse.of(order);
    }
}
