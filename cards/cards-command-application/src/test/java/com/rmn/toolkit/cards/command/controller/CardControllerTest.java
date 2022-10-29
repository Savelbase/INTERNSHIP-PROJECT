package com.rmn.toolkit.cards.command.controller;

import com.google.gson.Gson;
import com.rmn.toolkit.cards.command.advice.GlobalExceptionHandler;
import com.rmn.toolkit.cards.command.dto.request.CardDailyLimitDto;
import com.rmn.toolkit.cards.command.dto.request.CardStatusDto;
import com.rmn.toolkit.cards.command.model.Card;
import com.rmn.toolkit.cards.command.security.SecurityConfig;
import com.rmn.toolkit.cards.command.security.SecurityUtil;
import com.rmn.toolkit.cards.command.security.jwt.JwtUserDetailsService;
import com.rmn.toolkit.cards.command.security.jwt.JwtUtil;
import com.rmn.toolkit.cards.command.service.CardService;
import com.rmn.toolkit.cards.command.testUtil.EndpointUrlAndConstants;
import com.rmn.toolkit.cards.command.testUtil.RequestDtoBuilder;
import com.rmn.toolkit.cards.command.util.CardUtil;
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
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureWebMvc
@SpringBootTest(classes = {
        SecurityConfig.class,
        JwtUserDetailsService.class,
        JwtUtil.class,
        GlobalExceptionHandler.class,
        ResponseUtil.class,
        CardController.class,
        CardService.class,
        CardUtil.class,
        SecurityUtil.class,
        RequestDtoBuilder.class
}, properties = "spring.cloud.config.enabled=false")
@MockBeans({
        @MockBean(CardService.class),
        @MockBean(CardUtil.class),
        @MockBean(SecurityUtil.class),
        @MockBean(ResponseUtil.class)
})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CardControllerTest {
    private final MockMvc mockMvc;
    private final RequestDtoBuilder requestDtoBuilder;
    private final CardService service;
    private final CardUtil cardUtil;
    private final SecurityUtil securityUtil;

    @Test
    @WithMockUser(authorities = "CARD_EDIT")
    public void changeCardOrderStatusWithAuthorization() throws Exception {
        CardStatusDto cardStatusDto = requestDtoBuilder.createCardStatusDto();
        String json = convertRequestDtoToJson(cardStatusDto);
        Card card = requestDtoBuilder.createCard();

        doNothing().when(securityUtil).checkCurrentClientIdWithCardClientId(anyString());
        when(cardUtil.findCardById(anyString())).thenReturn(card);
        doNothing().when(service).changeCardStatusById(any(CardStatusDto.class));

        mockMvc.perform(patch(EndpointUrlAndConstants.CHANGE_CARD_STATUS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(securityUtil, times(1)).checkCurrentClientIdWithCardClientId(anyString());
        verify(cardUtil, times(1)).findCardById(anyString());
        verify(service, times(1)).changeCardStatusById(any(CardStatusDto.class));
    }

    @Test
    public void changeCardStatusWithoutAuthorization() throws Exception {
        CardStatusDto cardStatusDto = requestDtoBuilder.createCardStatusDto();
        String json = convertRequestDtoToJson(cardStatusDto);

        mockMvc.perform(patch(EndpointUrlAndConstants.CHANGE_CARD_STATUS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "CARD_EDIT")
    public void changeCardDailyLimitWithAuthorization() throws Exception {
        CardDailyLimitDto cardDailyLimitDto = requestDtoBuilder.createCardDailyLimitDto();
        String json = convertRequestDtoToJson(cardDailyLimitDto);

        doNothing().when(securityUtil).checkCurrentClientIdWithCardClientId(anyString());
        doNothing().when(service).changeCardDailyLimit(any(CardDailyLimitDto.class));

        mockMvc.perform(patch(EndpointUrlAndConstants.CHANGE_CARD_DAILY_LIMIT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(securityUtil, times(1)).checkCurrentClientIdWithCardClientId(anyString());
        verify(service, times(1)).changeCardDailyLimit(any(CardDailyLimitDto.class));
    }

    @Test
    public void changeCardDailyLimitWithoutAuthorization() throws Exception {
        CardDailyLimitDto cardDailyLimitDto = requestDtoBuilder.createCardDailyLimitDto();
        String json = convertRequestDtoToJson(cardDailyLimitDto);

        mockMvc.perform(patch(EndpointUrlAndConstants.CHANGE_CARD_DAILY_LIMIT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    private <T> String convertRequestDtoToJson(T requestDto) {
        Gson gson = new Gson();
        return gson.toJson(requestDto);
    }
}