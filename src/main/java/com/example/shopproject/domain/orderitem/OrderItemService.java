package com.example.shopproject.domain.orderitem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public List<OrderItemResponse> findAllByItemCodeDesc(String itemCode){
        return orderItemRepository.findAllByItemCodeDesc(itemCode).stream()
                .map(OrderItemResponse::of)
                .toList();
    }

}
