package com.example.shopproject.domain.order;

import com.example.shopproject.BaseEntity;
import com.example.shopproject.domain.orderitem.OrderItem;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "orders_id")
    private Long id;

    @Column(nullable = false)
    private int threadNum;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private int totalCount;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @Builder
    private Order(final int totalPrice, final int totalCount, final int threadNum,final OrderType orderType,final List<OrderItem> orderItems) {
        this.totalPrice = totalPrice;
        this.totalCount = totalCount;
        this.threadNum = threadNum;
        this.orderType = orderType;
        this.orderItems = orderItems;
    }

    public void addOrderItems(final OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.changeOrder(this);
    }

    public static Order createBy(final List<OrderItem> orderItems,final int threadNum,final OrderType orderType){
        Order order = Order.builder()
                .totalCount(computeTotalCount(orderItems))
                .totalPrice(computeTotalPrice(orderItems))
                .threadNum(threadNum)
                .orderType(orderType)
                .orderItems(orderItems)
                .build();

        orderItems.forEach(orderItem -> orderItem.changeOrder(order));
        return order;
    }

    private static int computeTotalCount(final List<OrderItem> orderItems){
        return orderItems.stream()
                .mapToInt(OrderItem::getCount)
                .sum();
    }

    private static int computeTotalPrice(final List<OrderItem> orderItems){
        return orderItems.stream()
                .mapToInt(item -> item.getItemPrice() * item.getCount())
                .sum();
    }

}
