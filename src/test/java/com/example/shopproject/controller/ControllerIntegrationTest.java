package com.example.shopproject.controller;

import com.example.shopproject.controller.item.ItemController;
import com.example.shopproject.controller.order.OrderController;
import com.example.shopproject.domain.item.ItemService;
import com.example.shopproject.domain.order.OrderFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers = {
        ItemController.class,
        OrderController.class
})
public abstract class ControllerIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected ItemService itemService;

    @MockBean
    protected OrderFacade orderFacade;

}
