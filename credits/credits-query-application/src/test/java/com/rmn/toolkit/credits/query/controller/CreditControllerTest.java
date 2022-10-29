package com.rmn.toolkit.credits.query.controller;

import com.rmn.toolkit.credits.query.advice.GlobalExceptionHandler;
import com.rmn.toolkit.credits.query.dto.response.success.CreditDto;
import com.rmn.toolkit.credits.query.security.SecurityConfig;
import com.rmn.toolkit.credits.query.security.SecurityUtil;
import com.rmn.toolkit.credits.query.security.jwt.JwtUserDetailsService;
import com.rmn.toolkit.credits.query.security.jwt.JwtUtil;
import com.rmn.toolkit.credits.query.service.CreditService;
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
        CreditController.class,
        CreditService.class,
        SecurityUtil.class
}, properties = "spring.cloud.config.enabled=false")
@MockBeans({
        @MockBean(CreditService.class),
        @MockBean(SecurityUtil.class),
        @MockBean(ResponseUtil.class)
})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CreditControllerTest {
    private final MockMvc mockMvc;
    private final CreditService service;
    private final SecurityUtil securityUtil;

    @Test
    @WithMockUser(authorities = "CREDIT_VIEW")
    public void getAllCreditsByClientIdWithAuthorization() throws Exception {
        List<CreditDto> credits = new ArrayList<>();
        when(securityUtil.getCurrentUserId()).thenReturn(EndpointUrlAndConstants.TEST_VALUE);
        when(service.getCreditsByClientId(anyString(), anyInt(), anyInt())).thenReturn(credits);

        mockMvc.perform(get(EndpointUrlAndConstants.GET_ALL_CREDITS))
                .andExpect(status().isOk());

        verify(securityUtil, times(1)).getCurrentUserId();
        verify(service, times(1)).getCreditsByClientId(anyString(), anyInt(), anyInt());
    }

    @Test
    public void getAllCreditsByClientIdWithoutAuthorization() throws Exception {
        mockMvc.perform(get(EndpointUrlAndConstants.GET_ALL_CREDITS))
                .andExpect(status().isForbidden());
    }
}

