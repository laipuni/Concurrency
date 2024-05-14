package com.example.shopproject.controller.item;

import com.example.shopproject.controller.ControllerIntegrationTest;
import com.example.shopproject.domain.order.OrderFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


class ItemControllerTest extends ControllerIntegrationTest {

    @DisplayName("물품들의 테이블을 보여준다")
    @Test
    void itemTable() throws Exception {
        //given//when//then
        mockMvc.perform(MockMvcRequestBuilders.get("/items"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(view().name("/item/items"));
    }

    @DisplayName("물품상세 정보를 보여준다")
    @Test
    void itemDetail() throws Exception {
        //given
        String itemCode = String.valueOf(UUID.randomUUID());

        //when//then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/items/" + itemCode)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(view().name("/item/itemDetail"));
    }
}