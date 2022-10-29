package com.rmn.toolkit.credits.query.controller;

import com.rmn.toolkit.credits.query.advice.GlobalExceptionHandler;
import com.rmn.toolkit.credits.query.dto.response.success.CreditOrderDto;
import com.rmn.toolkit.credits.query.security.SecurityConfig;
import com.rmn.toolkit.credits.query.security.SecurityUtil;
import com.rmn.toolkit.credits.query.security.jwt.JwtUserDetailsService;
import com.rmn.toolkit.credits.query.security.jwt.JwtUtil;
import com.rmn.toolkit.credits.query.service.CreditOrderService;
import com.rmn.toolkit.credits.query.testUtil.EndpointUrlAndConstants;
import com.rmn.toolkit.credits.query.testUtil.RequestDtoBuilder;
import com.rmn.toolkit.credits.query.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
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

import static org.mockito.ArgumentMatchers.anyInt;
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
        CreditOrderQueryController.class,
        CreditOrderService.class,
        SecurityUtil.class,
        RequestDtoBuilder.class
}, properties = "spring.cloud.config.enabled=false")
@MockBeans({
        @MockBean(CreditOrderService.class),
        @MockBean(SecurityUtil.class),
        @MockBean(ResponseUtil.class)
})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CreditOrderQueryControllerTest {
    private final MockMvc mockMvc;
    private final CreditOrderService service;
    private final SecurityUtil securityUtil;
    private final RequestDtoBuilder requestDtoBuilder;

    @Test
    @WithMockUser(authorities = "CREDIT_VIEW")
    public void getAllCreditOrderStatusesByClientIdWithAuthorization() throws Exception {
        List<CreditOrderDto> creditOrders = new ArrayList<>();
        doNothing().when(securityUtil).checkPermissionToViewCredits(anyString(), anyString());
        when(service.getCreditOrdersByClientId(anyString(), anyInt(), anyInt())).thenReturn(creditOrders);

        mockMvc.perform(get(EndpointUrlAndConstants.GET_ALL_CREDITS_ORDERS_BY_CLIENT_ID)
                        .header("Authorization", EndpointUrlAndConstants.TEST_VALUE)
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk());

        verify(securityUtil, times(1)).checkPermissionToViewCredits(anyString(), anyString());
        verify(service, times(1)).getCreditOrdersByClientId(anyString(), anyInt(), anyInt());
    }

    @Test
    public void getAllCreditOrderStatusesByClientIdWithoutAuthorization() throws Exception {
        mockMvc.perform(get(EndpointUrlAndConstants.GET_ALL_CREDITS_ORDERS_BY_CLIENT_ID)
                        .header("Authorization", EndpointUrlAndConstants.TEST_VALUE)
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "CREDIT_VIEW")
    public void getCreditOrderStatusByIdWithAuthorization() throws Exception {
        CreditOrderDto creditOrderDto = requestDtoBuilder.createCreditOrderDto();
        when(securityUtil.getCurrentUserId()).thenReturn(EndpointUrlAndConstants.TEST_VALUE);
        when(service.getCreditOrderByIdAndClientId(anyString(), anyString(), anyString())).thenReturn(creditOrderDto);

        mockMvc.perform(get(EndpointUrlAndConstants.GET_CREDIT_ORDER_BY_ID)
                        .header("Authorization", EndpointUrlAndConstants.TEST_VALUE))
                .andExpect(status().isOk());

        verify(securityUtil, times(1)).getCurrentUserId();
        verify(service, times(1)).getCreditOrderByIdAndClientId(anyString(), anyString(), anyString());
    }

    @Test
    public void getCreditOrderStatusByIdWithoutAuthorization() throws Exception {
        mockMvc.perform(get(EndpointUrlAndConstants.GET_CREDIT_ORDER_BY_ID)
                        .header("Authorization", EndpointUrlAndConstants.TEST_VALUE))
                .andExpect(status().isForbidden());
    }
}

