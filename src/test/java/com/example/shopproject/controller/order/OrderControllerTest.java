package com.example.shopproject.controller.order;

import com.example.shopproject.controller.ControllerIntegrationTest;
import com.example.shopproject.domain.order.OrderFacade;
import com.example.shopproject.domain.order.request.OrderCreateRequest;
import com.example.shopproject.domain.order.service.OptimisticOrderService;
import com.example.shopproject.domain.order.service.PessimisticOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class OrderControllerTest extends ControllerIntegrationTest {

    @DisplayName("/orders는 주문한 상품들을 보여준다.")
    @Test
    void test() throws Exception {
        //given//when//then
        mockMvc.perform(MockMvcRequestBuilders.get("/orders"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(view().name("/order/orders"));
    }

    @DisplayName("주문할 상품의 코드와 수량 쓰레드 수를 받아서 주문한 뒤 주문 목록 창으로 간다.")
    @Test
    void OrderRequestTest() throws Exception {
        //given
        OrderCreateRequest request = OrderCreateRequest.builder()
                .mode(PessimisticOrderService.class.getName())
                .itemCode(String.valueOf(UUID.randomUUID()))
                .itemCount(2)
                .threadNum(20)
                .build();

        String content = objectMapper.writeValueAsString(request);

        //when//then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/orders/new")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(content)
                )
                .andDo(print())
                .andExpect(view().name("redirect:/orders"));

    }

    @DisplayName("itemCode가 Null일 경우 BindException이 발생하고 home화면으로 돌아간다.")
    @Test
    void OrderRequestItemCodeTest() throws Exception {
        //given
        OrderCreateRequest request = OrderCreateRequest.builder()
                .mode(PessimisticOrderService.class.getName())
                .itemCount(2)
                .threadNum(20)
                .build();

        String content = objectMapper.writeValueAsString(request);

        //when//then
        mockMvc.perform(
                MockMvcRequestBuilders.post("/orders/new")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(content)
        )
                .andDo(print())
                .andExpect(view().name("/index"));

    }

    @DisplayName("itemCount가 음수일 경우 BindException이 발생하고 home화면으로 돌아간다.")
    @Test
    void OrderRequestItemCountTest() throws Exception {
        //given
        OrderCreateRequest request = OrderCreateRequest.builder()
                .mode(PessimisticOrderService.class.getName())
                .itemCount(-2)
                .itemCode(String.valueOf(UUID.randomUUID()))
                .threadNum(20)
                .build();

        String content = objectMapper.writeValueAsString(request);

        //when//then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/orders/new")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(content)
                )
                .andDo(print())
                .andExpect(view().name("/index"));

    }

    @DisplayName("threadNum이 음수일 경우 BindException이 발생하고 home화면으로 돌아간다.")
    @Test
    void OrderRequestThreadNumTest() throws Exception {
        //given
        OrderCreateRequest request = OrderCreateRequest.builder()
                .mode(PessimisticOrderService.class.getName())
                .itemCount(2)
                .itemCode(String.valueOf(UUID.randomUUID()))
                .threadNum(-20)
                .build();

        String content = objectMapper.writeValueAsString(request);
        //when//then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/orders/new")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(content)
                )
                .andDo(print())
                .andExpect(view().name("/index"));

    }

    @DisplayName("모드가 Null일 경우 BindException이 발생하고 home화면으로 돌아간다.")
    @Test
    void OrderRequestModeTest() throws Exception {
        //given
        OrderCreateRequest request = OrderCreateRequest.builder()
                .itemCount(2)
                .itemCode(String.valueOf(UUID.randomUUID()))
                .threadNum(20)
                .build();

        String content = objectMapper.writeValueAsString(request);

        //when//then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/orders/new")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(content)
                )
                .andDo(print())
                .andExpect(view().name("/index"));

    }

    @DisplayName("물품 코드를 받아 물품의 주문 정보들을 가져온 뒤 model로 넘겨 물품 상세 주문정보 페이지로 간다.")
    @Test
    void showOrdersAboutItem() throws Exception {
        //given
        String itemCode = String.valueOf(UUID.randomUUID());

        //when//then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/orders/" + itemCode)
                                .param("itemCode",itemCode)
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("/order/ItemOrder"));

    }
}