package com.example.shopproject.domain.order;

import com.example.shopproject.domain.item.Item;
import com.example.shopproject.domain.item.ItemRepository.ItemRepository;
import com.example.shopproject.domain.order.request.OrderCreateRequest;
import com.example.shopproject.domain.order.response.OrderCreateResponse;
import com.example.shopproject.domain.orderitem.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TestOrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public OrderCreateResponse createOrders(OrderCreateRequest request){

        //구매할 물품을 조회
        Item item = itemRepository.findByItemCode(request.getItemCode())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 물품입니다."));

        //구매할 물품을 주문할 물품에 담기
        OrderItem orderItem = OrderItem.create(item, request.getItemCount());

        Order order = Order.createBy(List.of(orderItem));
        orderRepository.save(order);

        return OrderCreateResponse.of(order);
    }


}
