package com.rmn.toolkit.cards.command.controller;

import com.google.gson.Gson;
import com.rmn.toolkit.cards.command.dto.request.CardOrderDto;
import com.rmn.toolkit.cards.command.dto.request.CardOrderStatusDto;
import com.rmn.toolkit.cards.command.model.CardOrder;
import com.rmn.toolkit.cards.command.model.type.CardOrderStatusType;
import com.rmn.toolkit.cards.command.service.CardOrderService;
import com.rmn.toolkit.cards.command.testUtil.EndpointUrlAndConstants;
import com.rmn.toolkit.cards.command.testUtil.RequestDtoBuilder;
import com.rmn.toolkit.cards.command.advice.GlobalExceptionHandler;
import com.rmn.toolkit.cards.command.security.SecurityConfig;
import com.rmn.toolkit.cards.command.security.SecurityUtil;
import com.rmn.toolkit.cards.command.security.jwt.JwtUserDetailsService;
import com.rmn.toolkit.cards.command.security.jwt.JwtUtil;
import com.rmn.toolkit.cards.command.util.CardOrderUtil;
import com.rmn.toolkit.cards.command.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureWebMvc
@SpringBootTest(classes = {
        SecurityConfig.class,
        JwtUserDetailsService.class,
        JwtUtil.class,
        GlobalExceptionHandler.class,
        ResponseUtil.class,
        CardOrderController.class,
        CardOrderService.class,
        CardOrderUtil.class,
        SecurityUtil.class,
        RequestDtoBuilder.class
}, properties = "spring.cloud.config.enabled=false")
@MockBeans({
        @MockBean(CardOrderService.class),
        @MockBean(CardOrderUtil.class),
        @MockBean(SecurityUtil.class),
        @MockBean(ResponseUtil.class)
})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CardOrderControllerTest {
    private final MockMvc mockMvc;
    private final RequestDtoBuilder requestDtoBuilder;
    private final CardOrderService service;
    private final CardOrderUtil cardOrderUtil;
    private final SecurityUtil securityUtil;

    @Test
    @WithMockUser(authorities = "CARD_EDIT")
    public void createCardOrderWithAuthorization() throws Exception {
        CardOrderDto cardOrderDto = requestDtoBuilder.createCardOrderDto(true);
        String json = convertRequestDtoToJson(cardOrderDto);
        CardOrder cardOrder = requestDtoBuilder.createCardOrder();

        when(securityUtil.getCurrentUserId()).thenReturn(EndpointUrlAndConstants.TEST_VALUE);
        when(service.createCardOrder(any(CardOrderDto.class), anyString())).thenReturn(cardOrder);

        mockMvc.perform(post(EndpointUrlAndConstants.CARD_ORDER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(securityUtil, times(1)).getCurrentUserId();
        verify(service, times(1)).createCardOrder(any(CardOrderDto.class), anyString());
    }

    @Test
    public void createCardOrderWithoutAuthorization() throws Exception {
        CardOrderDto cardOrderDto = requestDtoBuilder.createCardOrderDto(true);
        String json = convertRequestDtoToJson(cardOrderDto);

        mockMvc.perform(post(EndpointUrlAndConstants.CARD_ORDER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "EDIT_CARD_ORDER_STATUS")
    public void changeCardOrderStatusWithAuthorization() throws Exception {
        CardOrderStatusDto cardOrderStatusDto = requestDtoBuilder.createCardOrderStatusDto(CardOrderStatusType.APPROVED);
        String json = convertRequestDtoToJson(cardOrderStatusDto);
        CardOrder cardOrder = requestDtoBuilder.createCardOrder();

        when(cardOrderUtil.findCardOrderById(anyString())).thenReturn(cardOrder);
        when(securityUtil.getCurrentUserId()).thenReturn(EndpointUrlAndConstants.TEST_VALUE);
        doNothing().when(service).changeCardOrderStatusById(any(CardOrder.class), any(CardOrderStatusDto.class), anyString());

        mockMvc.perform(patch(EndpointUrlAndConstants.CARD_ORDER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(cardOrderUtil, times(1)).findCardOrderById(anyString());
        verify(securityUtil, times(1)).getCurrentUserId();
        verify(service, times(1)).changeCardOrderStatusById(any(CardOrder.class), any(CardOrderStatusDto.class), anyString());
    }

    @Test
    public void changeCardOrderStatusWithoutAuthorization() throws Exception {
        CardOrderStatusDto cardOrderStatusDto = requestDtoBuilder.createCardOrderStatusDto(CardOrderStatusType.APPROVED);
        String json = convertRequestDtoToJson(cardOrderStatusDto);

        mockMvc.perform(patch(EndpointUrlAndConstants.CARD_ORDER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    private <T> String convertRequestDtoToJson(T requestDto) {
        Gson gson = new Gson();
        return gson.toJson(requestDto);
    }
}

