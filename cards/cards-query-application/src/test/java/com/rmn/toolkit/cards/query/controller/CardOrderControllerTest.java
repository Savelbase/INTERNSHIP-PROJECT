package com.rmn.toolkit.cards.query.controller;

import com.rmn.toolkit.cards.query.advice.GlobalExceptionHandler;
import com.rmn.toolkit.cards.query.dto.response.success.CardOrderDto;
import com.rmn.toolkit.cards.query.security.SecurityConfig;
import com.rmn.toolkit.cards.query.security.SecurityUtil;
import com.rmn.toolkit.cards.query.security.jwt.JwtUserDetailsService;
import com.rmn.toolkit.cards.query.security.jwt.JwtUtil;
import com.rmn.toolkit.cards.query.service.CardOrderService;
import com.rmn.toolkit.cards.query.testUtil.EndpointUrlAndConstants;
import com.rmn.toolkit.cards.query.testUtil.RequestDtoBuilder;
import com.rmn.toolkit.cards.query.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        SecurityUtil.class,
        RequestDtoBuilder.class
}, properties = "spring.cloud.config.enabled=false")
@MockBeans({
        @MockBean(CardOrderService.class),
        @MockBean(SecurityUtil.class),
        @MockBean(ResponseUtil.class)
})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CardOrderControllerTest {
    private final MockMvc mockMvc;
    private final CardOrderService service;
    private final SecurityUtil securityUtil;
    private final RequestDtoBuilder requestDtoBuilder;

    @Test
    @WithMockUser(authorities = "CARD_VIEW")
    public void getAllCardOrdersByClientIdWithAuthorization() throws Exception {
        List<CardOrderDto> cardOrders = new ArrayList<>();
        doNothing().when(securityUtil).checkPermissionToViewCardOrder(anyString(), anyString());
        when(service.getAllCardOrdersByClientId(anyString(), anyInt(), anyInt())).thenReturn(cardOrders);

        mockMvc.perform(get(EndpointUrlAndConstants.GET_ALL_CARDS_ORDERS_BY_CLIENT_ID)
                        .header(HttpHeaders.AUTHORIZATION, EndpointUrlAndConstants.TEST_VALUE))
                .andExpect(status().isOk());

        verify(securityUtil, times(1)).checkPermissionToViewCardOrder(anyString(), anyString());
        verify(service, times(1)).getAllCardOrdersByClientId(anyString(), anyInt(), anyInt());
    }

    @Test
    public void getAllCardOrdersByClientIdWithoutAuthorization() throws Exception {
        mockMvc.perform(get(EndpointUrlAndConstants.GET_ALL_CARDS_ORDERS_BY_CLIENT_ID)
                        .header(HttpHeaders.AUTHORIZATION, EndpointUrlAndConstants.TEST_VALUE))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "CARD_VIEW")
    public void getCardOrderStatusByIdWithAuthorization() throws Exception {
        CardOrderDto cardOrderDto = requestDtoBuilder.createCardOrderDto();
        when(securityUtil.getCurrentUserId()).thenReturn(EndpointUrlAndConstants.TEST_VALUE);
        when(service.getCardOrderByIdAndClientId(anyString(), anyString(), anyString())).thenReturn(cardOrderDto);

        mockMvc.perform(get(EndpointUrlAndConstants.GET_CARD_ORDER)
                        .header(HttpHeaders.AUTHORIZATION, EndpointUrlAndConstants.TEST_VALUE))
                .andExpect(status().isOk());

        verify(securityUtil, times(1)).getCurrentUserId();
        verify(service, times(1)).getCardOrderByIdAndClientId(anyString(), anyString(), anyString());
    }

    @Test
    public void getCardOrderStatusByIdWithoutAuthorization() throws Exception {
        mockMvc.perform(get(EndpointUrlAndConstants.GET_CARD_ORDER)
                        .header(HttpHeaders.AUTHORIZATION, EndpointUrlAndConstants.TEST_VALUE))
                .andExpect(status().isForbidden());
    }
}
