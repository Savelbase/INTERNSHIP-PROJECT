package com.rmn.toolkit.cards.query.controller;

import com.rmn.toolkit.cards.query.advice.GlobalExceptionHandler;
import com.rmn.toolkit.cards.query.dto.response.success.CardDto;
import com.rmn.toolkit.cards.query.security.SecurityConfig;
import com.rmn.toolkit.cards.query.security.SecurityUtil;
import com.rmn.toolkit.cards.query.security.jwt.JwtUserDetailsService;
import com.rmn.toolkit.cards.query.security.jwt.JwtUtil;
import com.rmn.toolkit.cards.query.service.CardQueryService;
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

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
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
        CardQueryController.class,
        CardQueryService.class,
        SecurityUtil.class
}, properties = "spring.cloud.config.enabled=false")
@MockBeans({
        @MockBean(CardQueryService.class),
        @MockBean(SecurityUtil.class),
        @MockBean(ResponseUtil.class)
})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CardQueryControllerTest {
    private final MockMvc mockMvc;
    private final CardQueryService service;
    private final SecurityUtil securityUtil;

    @Test
    @WithMockUser(authorities = "CARD_VIEW")
    public void getCardByCardIdWithAuthorization() throws Exception {
        doNothing().when(securityUtil).checkCurrentClientIdWithCardClientId(anyString());
        when(service.getCardDtoByCardId(anyString())).thenReturn(any(CardDto.class));

        mockMvc.perform(get(EndpointUrlAndConstants.GET_CARD_BY_CARD_ID))
                .andExpect(status().isOk());

        verify(securityUtil, times(1)).checkCurrentClientIdWithCardClientId(anyString());
        verify(service, times(1)).getCardDtoByCardId(anyString());
    }

    @Test
    public void getCardByCardIdWithoutAuthorization() throws Exception {
        mockMvc.perform(get(EndpointUrlAndConstants.GET_CARD_BY_CARD_ID))
                .andExpect(status().isForbidden());
    }
    @Test
    @WithMockUser(authorities = "CARD_VIEW")
    public void getCardByCardsWithAuthorizationByClientId() throws Exception {
        when(securityUtil.getCurrentUserId()).thenReturn(EndpointUrlAndConstants.TEST_ID);
        when(service.getCardsDtoByClientId(anyString())).thenReturn(new ArrayList<>());

        mockMvc.perform(get(EndpointUrlAndConstants.URL))
                .andExpect(status().isOk());

        verify(securityUtil, times(1)).getCurrentUserId();
        verify(service, times(1)).getCardsDtoByClientId(anyString());
    }

    @Test
    public void getCardByCardsByClientIdWithoutAuthorization() throws Exception {
        mockMvc.perform(get(EndpointUrlAndConstants.URL))
                .andExpect(status().isForbidden());
    }
}
