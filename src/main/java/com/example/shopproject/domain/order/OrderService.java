package com.example.shopproject.domain.order;

import com.example.shopproject.domain.item.Item;
import com.example.shopproject.domain.item.ItemRepository;
import com.example.shopproject.domain.member.Member;
import com.example.shopproject.domain.member.MemberRepository;
import com.example.shopproject.domain.member.MemberService;
import com.example.shopproject.domain.order.request.OrderCreateRequest;
import com.example.shopproject.domain.order.response.OrderCreateResponse;
import com.example.shopproject.domain.orderitem.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public OrderCreateResponse createOrders(OrderCreateRequest request, Long memberId, LocalDate orderDate){

        //구매할 유저의 정보를 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        //구매할 물품을 조회
        Item item = itemRepository.findByItemCode(request.getItemCode())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 물품입니다."));

        //구매할 물품을 주문할 물품에 담기
        OrderItem orderItem = OrderItem.create(item, request.getItemCount());

        Order order = Order.createBy(member, orderDate, List.of(orderItem));
        orderRepository.save(order);

        return OrderCreateResponse.of(order);
    }


}
