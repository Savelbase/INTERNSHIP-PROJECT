package com.rmn.toolkit.credits.query.controller;

import com.rmn.toolkit.credits.query.advice.GlobalExceptionHandler;
import com.rmn.toolkit.credits.query.dto.response.success.CreditProductDto;
import com.rmn.toolkit.credits.query.dto.response.success.CreditProductAgreementDto;
import com.rmn.toolkit.credits.query.security.SecurityConfig;
import com.rmn.toolkit.credits.query.security.jwt.JwtUserDetailsService;
import com.rmn.toolkit.credits.query.security.jwt.JwtUtil;
import com.rmn.toolkit.credits.query.service.CreditDictionaryService;
import com.rmn.toolkit.credits.query.testUtil.EndpointUrlAndConstants;
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
        CreditDictionaryController.class,
        CreditDictionaryService.class
}, properties = "spring.cloud.config.enabled=false")
@MockBeans({
        @MockBean(CreditDictionaryService.class),
        @MockBean(ResponseUtil.class)
})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CreditDictionaryControllerTest {
    private final MockMvc mockMvc;
    private final CreditDictionaryService service;

    @Test
    @WithMockUser(authorities = "CREDIT_VIEW")
    public void getAllCreditProductsWithAuthorization() throws Exception {
        List<CreditProductDto> creditProducts = new ArrayList<>();
        when(service.getAllCreditProducts(anyInt(), anyInt())).thenReturn(creditProducts);

        mockMvc.perform(get(EndpointUrlAndConstants.GET_ALL_CREDITS_PRODUCTS)
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk());

        verify(service, times(1)).getAllCreditProducts(anyInt(), anyInt());
    }

    @Test
    public void getAllCreditProductsWithoutAuthorization() throws Exception {
        mockMvc.perform(get(EndpointUrlAndConstants.GET_ALL_CREDITS_PRODUCTS)
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "CREDIT_VIEW")
    public void getCreditProductByIdWithAuthorization() throws Exception {
        when(service.getCreditProductById(anyString())).thenReturn(any(CreditProductDto.class));

        mockMvc.perform(get(EndpointUrlAndConstants.GET_CREDIT_PRODUCT_BY_ID))
                .andExpect(status().isOk());

        verify(service, times(1)).getCreditProductById(anyString());
    }

    @Test
    public void getCreditProductByIdWithoutAuthorization() throws Exception {
        mockMvc.perform(get(EndpointUrlAndConstants.GET_CREDIT_PRODUCT_BY_ID))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "CREDIT_VIEW")
    public void getCreditProductAgreementByIdWithAuthorization() throws Exception {
        when(service.getCreditProductAgreementById(anyString())).thenReturn(any(CreditProductAgreementDto.class));

        mockMvc.perform(get(EndpointUrlAndConstants.GET_CREDIT_PRODUCT_AGREEMENT_BY_ID))
                .andExpect(status().isOk());

        verify(service, times(1)).getCreditProductAgreementById(anyString());
    }

    @Test
    public void getCreditProductAgreementByIdWithoutAuthorization() throws Exception {
        mockMvc.perform(get(EndpointUrlAndConstants.GET_CREDIT_PRODUCT_AGREEMENT_BY_ID))
                .andExpect(status().isForbidden());
    }
}

