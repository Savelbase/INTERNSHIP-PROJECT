package com.rmn.toolkit.cards.query.controller;

import com.rmn.toolkit.cards.query.advice.GlobalExceptionHandler;
import com.rmn.toolkit.cards.query.dto.response.success.CardRequisitesDto;
import com.rmn.toolkit.cards.query.security.SecurityConfig;
import com.rmn.toolkit.cards.query.security.SecurityUtil;
import com.rmn.toolkit.cards.query.security.jwt.JwtUserDetailsService;
import com.rmn.toolkit.cards.query.security.jwt.JwtUtil;
import com.rmn.toolkit.cards.query.service.CardRequisitesService;
import com.rmn.toolkit.cards.query.testUtil.EndpointUrlAndConstants;
import com.rmn.toolkit.cards.query.util.ResponseUtil;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
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
        CardRequisitesController.class,
        CardRequisitesService.class,
        SecurityUtil.class
}, properties = "spring.cloud.config.enabled=false")
@MockBeans({
        @MockBean(CardRequisitesService.class),
        @MockBean(SecurityUtil.class),
        @MockBean(ResponseUtil.class)
})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CardRequisitesControllerTest {
    private final MockMvc mockMvc;
    private final CardRequisitesService service;
    private final SecurityUtil securityUtil;

    @Test
    @WithMockUser(authorities = "CARD_VIEW")
    public void getCardRequisitesByCardIdWithAuthorization() throws Exception {
        doNothing().when(securityUtil).checkCurrentClientIdWithCardClientId(anyString());
        when(service.getCardRequisitesByCardId(anyString())).thenReturn(any(CardRequisitesDto.class));

        mockMvc.perform(get(EndpointUrlAndConstants.GET_CARD_REQUISITES_BY_CARD_ID))
                .andExpect(status().isOk());

        verify(securityUtil, times(1)).checkCurrentClientIdWithCardClientId(anyString());
        verify(service, times(1)).getCardRequisitesByCardId(anyString());
    }

    @Test
    public void getCardRequisitesByCardIdWithoutAuthorization() throws Exception {
        mockMvc.perform(get(EndpointUrlAndConstants.GET_CARD_REQUISITES_BY_CARD_ID))
                .andExpect(status().isForbidden());
    }
}
