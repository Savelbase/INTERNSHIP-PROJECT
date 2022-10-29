package com.rmn.toolkit.cedits.command.controller;

import com.google.gson.Gson;
import com.rmn.toolkit.cedits.command.testUtil.EndpointUrlAndConstants;
import com.rmn.toolkit.cedits.command.testUtil.RequestDtoBuilder;
import com.rmn.toolkit.credits.command.advice.GlobalExceptionHandler;
import com.rmn.toolkit.credits.command.controller.CreditOrderCommandController;
import com.rmn.toolkit.credits.command.dto.request.CreditOrderDto;
import com.rmn.toolkit.credits.command.dto.request.CreditOrderStatusDto;
import com.rmn.toolkit.credits.command.model.CreditOrder;
import com.rmn.toolkit.credits.command.security.SecurityConfig;
import com.rmn.toolkit.credits.command.security.SecurityUtil;
import com.rmn.toolkit.credits.command.security.jwt.JwtUserDetailsService;
import com.rmn.toolkit.credits.command.security.jwt.JwtUtil;
import com.rmn.toolkit.credits.command.service.CreditOrderService;
import com.rmn.toolkit.credits.command.util.CreditOrderUtil;
import com.rmn.toolkit.credits.command.util.ResponseUtil;
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
        CreditOrderCommandController.class,
        CreditOrderService.class,
        CreditOrderUtil.class,
        SecurityUtil.class,
        RequestDtoBuilder.class
}, properties = "spring.cloud.config.enabled=false")
@MockBeans({
        @MockBean(CreditOrderService.class),
        @MockBean(CreditOrderUtil.class),
        @MockBean(SecurityUtil.class),
        @MockBean(ResponseUtil.class)
})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CreditOrderCommandControllerTest {
    private final MockMvc mockMvc;
    private final RequestDtoBuilder requestDtoBuilder;
    private final CreditOrderService service;
    private final CreditOrderUtil creditOrderUtil;
    private final SecurityUtil securityUtil;

    @Test
    @WithMockUser(authorities = "CREDIT_EDIT")
    public void createCreditOrderWithAuthorization() throws Exception {
        CreditOrderDto creditOrderDto = requestDtoBuilder.createCreditOrderDto();
        String json = convertRequestDtoToJson(creditOrderDto);
        CreditOrder creditOrder = requestDtoBuilder.createCreditOrder();

        when(securityUtil.getCurrentUserId()).thenReturn(EndpointUrlAndConstants.TEST_VALUE);
        when(service.createCreditOrder(anyString(), any(CreditOrderDto.class))).thenReturn(creditOrder);

        mockMvc.perform(post(EndpointUrlAndConstants.URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(securityUtil, times(1)).getCurrentUserId();
        verify(service, times(1)).createCreditOrder(anyString(), any(CreditOrderDto.class));
    }

    @Test
    public void createCreditOrderWithoutAuthorization() throws Exception {
        CreditOrderDto creditOrderDto = requestDtoBuilder.createCreditOrderDto();
        String json = convertRequestDtoToJson(creditOrderDto);

        mockMvc.perform(post(EndpointUrlAndConstants.URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "EDIT_CREDIT_ORDER_STATUS")
    public void changeCreditOrderStatusWithAuthorization() throws Exception {
        CreditOrderStatusDto creditOrderStatusDto = requestDtoBuilder.createCreditOrderStatusDto();
        String json = convertRequestDtoToJson(creditOrderStatusDto);
        CreditOrder creditOrder = requestDtoBuilder.createCreditOrder();

        when(creditOrderUtil.findCreditOrderById(anyString())).thenReturn(creditOrder);
        when(securityUtil.getCurrentUserId()).thenReturn(EndpointUrlAndConstants.TEST_VALUE);
        doNothing().when(service).changeCreditOrderStatusById(any(CreditOrder.class), any(CreditOrderStatusDto.class), anyString());

        mockMvc.perform(patch(EndpointUrlAndConstants.URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(creditOrderUtil, times(1)).findCreditOrderById(anyString());
        verify(securityUtil, times(1)).getCurrentUserId();
        verify(service, times(1)).changeCreditOrderStatusById(any(CreditOrder.class), any(CreditOrderStatusDto.class), anyString());
    }

    @Test
    public void changeCreditOrderStatusWithoutAuthorization() throws Exception {
        CreditOrderStatusDto creditOrderStatusDto = requestDtoBuilder.createCreditOrderStatusDto();
        String json = convertRequestDtoToJson(creditOrderStatusDto);

        mockMvc.perform(patch(EndpointUrlAndConstants.URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    private <T> String convertRequestDtoToJson(T requestDto) {
        Gson gson = new Gson();
        return gson.toJson(requestDto);
    }
}

