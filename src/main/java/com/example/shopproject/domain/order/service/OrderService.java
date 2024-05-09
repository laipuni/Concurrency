package com.example.shopproject.domain.order.service;

import com.example.shopproject.domain.order.response.OrderCreateResponse;
import org.springframework.stereotype.Service;

public interface OrderService {

    public OrderCreateResponse orderBy(final String itemCode, int Quantity);

}
