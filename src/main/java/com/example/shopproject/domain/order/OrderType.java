package com.example.shopproject.domain.order;

import com.example.shopproject.domain.order.service.OptimisticOrderService;
import com.example.shopproject.domain.order.service.PessimisticOrderService;

import java.util.*;
import java.util.stream.Collectors;

public enum OrderType {

    PESSIMISTIC(PessimisticOrderService.class.getName()),
    OPTIMISTIC(OptimisticOrderService.class.getName());

    private String name;
    private static final Map<String,OrderType> orderTypeMap = createOrderTypeMap();

    private static HashMap<String, OrderType> createOrderTypeMap() {
        return new HashMap<>(
                Arrays.stream(OrderType.values())
                        .collect(Collectors.toMap(OrderType::getName, OrderType -> OrderType))
        );
    }

    OrderType(final String name) {
        this.name = name;
    }

    public static OrderType findOrderType(String type){
        return Optional.ofNullable(orderTypeMap.get(type))
                .orElseThrow(() -> new IllegalArgumentException("해당하는 OrderType은 존재하지 않습니다."));
    }

    public String getName() {
        return name;
    }
}
