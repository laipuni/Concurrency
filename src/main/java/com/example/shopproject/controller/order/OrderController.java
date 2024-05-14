package com.example.shopproject.controller.order;

import com.example.shopproject.domain.order.OrderFacade;
import com.example.shopproject.domain.order.request.OrderCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderFacade orderFacade;

    @GetMapping("/orders")
    public String orders(){
        return "/order/orders";
    }

    /**
     * 상품 주문
     * 주문할 수량과 방식을 골라 버튼을 눌렀을 경우 주문을 등록한다.
     */
    @PostMapping("/orders/new")
    public String orderByThreadNum(@Valid @ModelAttribute OrderCreateRequest request, BindingResult result){
        if(result.hasErrors()){
            return "/index";
        }
        orderFacade.orderByThreadNum(request);
        return "redirect:/orders";
    }


}
