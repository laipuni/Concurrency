package com.example.shopproject.domain.order;

import com.example.shopproject.domain.IntegrationTestSupport;
import com.example.shopproject.domain.item.Item;
import com.example.shopproject.domain.item.ItemRepository;
import com.example.shopproject.domain.member.Member;
import com.example.shopproject.domain.member.MemberRepository;
import com.example.shopproject.domain.order.request.OrderCreateRequest;
import com.example.shopproject.domain.orderitem.OrderItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;


class OrderServiceTest extends IntegrationTestSupport {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderRepository orderRepository;


    @DisplayName("주문할 제품의 정보와 수량을 받아서 주문이 등록되고 물품의 수량이 감소한다.")
    @Test
    void test(){
        //given
        int count = 5;
        String itemCode = String.valueOf(UUID.randomUUID());
        LocalDate date = LocalDate.of(2024,4,10);
        OrderCreateRequest request = OrderCreateRequest.builder()
                .itemCode(itemCode)
                .itemCount(5)
                .build();
        Member member =Member.builder()
                .nickname("라이푸니")
                .age(15)
                .build();

        Item item = Item.builder()
                .itemCode(itemCode)
                .price(1700)
                .itemName("삼각김밥")
                .quantity(30)
                .build();

        List<OrderItem> orderItems = List.of(OrderItem.create(item, count));

        Order order = Order.createBy(member, date, orderItems);

        memberRepository.save(member);
        itemRepository.save(item);
        orderService.createOrders(request,member.getId(),LocalDate.of(2024,4,10));

        //when
        List<Order> orders = orderRepository.findAll();

        //then
        assertThat(orders).hasSize(1)
                .extracting("totalPrice","totalCount","orderStatus","orderDate")
                .containsExactly(tuple(8500,5,OrderStatus.INIT,date));
        assertThat(item.getQuantity()).isEqualTo(25);
    }

    @DisplayName("다수의 인원이 동시에 주문을 할 경우 적절하게 물품의 수량이 감소하지 않는다")
    @Test
    void createOrderNoLock(){
        //given

        //when

        //then
    }

    @DisplayName("다수의 인원이 동시에 주문을 할 경우 적절하게 물품의 수량이 감소한다.")
    @Test
    void createOrderWithPessimisticLock(){
        //given

        //when

        //then
    }


    @DisplayName("다수의 인원이 동시에 주문을 할 경우 적절하게 물품의 수량이 감소한다.")
    @Test
    void createOrderWithOptimisticLock(){
        //given

        //when

        //then
    }

    @DisplayName("Syncronized방식으로는 스프링의 @Transactional기능 때문에 동시성 문제가 발생해 적절하게 감소하지 않는다.")
    @Test
    void createOrderWithSyncronized(){
        //given

        //when

        //then
    }
}