package com.example.shopproject.domain.order.service;

import com.example.shopproject.domain.item.Item;
import com.example.shopproject.domain.item.ItemRepository.ItemRepository;
import com.example.shopproject.domain.order.Order;
import com.example.shopproject.domain.order.OrderRepository;
import com.example.shopproject.domain.order.response.OrderCreateResponse;
import com.example.shopproject.domain.order.service.OrderService;
import com.example.shopproject.domain.orderitem.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PessimisticOrderService implements OrderService {

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    @Transactional
    @Override
    public OrderCreateResponse orderBy(final String itemCode, final int Quantity) {
        Item item = itemRepository.findByItemCodeForUpdate(itemCode)
                .orElseThrow(() -> new IllegalArgumentException("해당 물품은 존재하지 않습니다."));
        OrderItem orderItem = OrderItem.create(item,Quantity);
        Order order = Order.createBy(List.of(orderItem));
        orderRepository.save(order);
        return OrderCreateResponse.of(order);
    }
}
