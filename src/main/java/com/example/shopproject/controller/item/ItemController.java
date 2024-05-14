package com.example.shopproject.controller.item;

import com.example.shopproject.domain.item.response.ItemDetailResponse;
import com.example.shopproject.domain.item.response.ItemListResponse;
import com.example.shopproject.domain.item.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor()
public class ItemController {

    private final ItemService itemService;
    /**
     * 상품들의 종류들을 테이블 형식으로 추상적이게 보여준다.
     * @return 상품리스트 페이지
     */
    @GetMapping("/items")
    public String itemTable(Model model){
        List<ItemListResponse> items = itemService.findAll();
        model.addAttribute("items",items);
        return "/item/items";
    }

    /**
     * 상품 상세 페이지
     * 상품을 정보와 주문할 수량과 방식을 골라 주문할 수 있는 페이지를 보여준다.
     * @return 상품상세 페이지
     */
    @GetMapping("/items/{itemCode}")
    public String itemDetail(@PathVariable("itemCode") String itemCode,Model model){
        log.info("itemCode = {}",itemCode);
        ItemDetailResponse item = itemService.findByItemCode(itemCode);
        model.addAttribute("item",item);
        return "/item/itemDetail";
    }


}
