package com.example.shopproject.controller.order;

import com.example.shopproject.domain.order.OrderFacade;
import com.example.shopproject.domain.order.request.OrderCreateRequest;
import com.example.shopproject.domain.order.service.OrderService;
import com.example.shopproject.domain.orderitem.OrderItemResponse;
import com.example.shopproject.domain.orderitem.OrderItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderFacade orderFacade;
    private final OrderItemService orderItemService;
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
            log.info("erorr = {}",result);
            return "/index";
        }
        log.info("itemCode = {}, itemCount = {}, mode = {}",request.getItemCode(),request.getItemCount(),request.getMode());

        try {
            orderFacade.orderByThreadNum(request);
        } catch (IllegalArgumentException e){
            //재고가 부족 하거나 없는 상품을 구매할 경우
            //일부 쓰레드들은 재고가 부족해 구매가 불가능했는 경우
            //todo 다시 주문 창으로 가야함
            return "redirect:/items";
        }
        return "redirect:/orders";
    }

    @GetMapping("/orders/{itemCode}")
    public String showOrdersAboutItem(@PathVariable("itemCode") String itemCode, Model model){
        List<OrderItemResponse> orders = orderItemService.findAllByItemCodeDesc(itemCode);
        model.addAttribute("orders",orders);
        return "/order/ItemOrder";
    }
}
