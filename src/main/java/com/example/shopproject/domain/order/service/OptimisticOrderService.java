package com.example.shopproject.domain.order.service;

import com.example.shopproject.domain.item.Item;
import com.example.shopproject.domain.item.ItemRepository.ItemRepository;
import com.example.shopproject.domain.order.Order;
import com.example.shopproject.domain.order.OrderRepository;
import com.example.shopproject.domain.order.response.OrderCreateResponse;
import com.example.shopproject.domain.orderitem.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OptimisticOrderService implements OrderService{

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    @Transactional
    @Override
    public OrderCreateResponse orderBy(final String itemCode, final int Quantity) {
        //todo implement findByItemCode Optimistic version in ItemRepository

        //OrderItem orderItem = OrderItem.create(item,Quantity);

        //Order order = Order.createBy(List.of(orderItem));
        return null;
    }
}
