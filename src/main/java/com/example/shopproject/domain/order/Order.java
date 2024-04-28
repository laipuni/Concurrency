package com.example.shopproject.domain.order;

import com.example.shopproject.BaseEntity;
import com.example.shopproject.domain.member.Member;
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
    private int totalPrice;

    @Column(nullable = false)
    private int totalCount;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    private LocalDate orderDate;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private Order(
            final int totalPrice, final int totalCount,
                  final OrderStatus orderStatus, final LocalDate orderDate,
                  final Member member
    ) {
        this.totalPrice = totalPrice;
        this.totalCount = totalCount;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.member = member;
    }

    //연관관계 메소드들

    public void addOrderItems(final OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public static Order createBy(final Member member,
                                 final LocalDate orderDate, final List<OrderItem> orderItem){
        final Order order = Order.builder()
                .member(member)
                .orderDate(orderDate)
                .orderStatus(OrderStatus.INIT)
                .totalCount(computeTotalCount(orderItem))
                .totalPrice(computeTotalPrice(orderItem))
                .build();

        member.addOrder(order);
        for (OrderItem item : orderItem) {
            order.addOrderItems(item);
        }
        return order;
    }

    private static int computeTotalCount(final List<OrderItem> orderItem){
        return orderItem.stream()
                .mapToInt(OrderItem::getCount)
                .sum();
    }

    private static int computeTotalPrice(final List<OrderItem> orderItem){
        return orderItem.stream()
                .mapToInt(item -> item.getItemPrice() * item.getCount())
                .sum();
    }

}
