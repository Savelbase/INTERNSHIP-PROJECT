package com.rmn.toolkit.cards.query.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rmn.toolkit.cards.query.advice.GlobalExceptionHandler;
import com.rmn.toolkit.cards.query.dto.request.CardReceiptsFilters;
import com.rmn.toolkit.cards.query.dto.request.CardStatementsPeriodDto;
import com.rmn.toolkit.cards.query.dto.request.TransactionTypeDto;
import com.rmn.toolkit.cards.query.dto.response.success.ReceiptDto;
import com.rmn.toolkit.cards.query.security.SecurityConfig;
import com.rmn.toolkit.cards.query.security.SecurityUtil;
import com.rmn.toolkit.cards.query.security.jwt.JwtUserDetailsService;
import com.rmn.toolkit.cards.query.security.jwt.JwtUtil;
import com.rmn.toolkit.cards.query.service.CreatePDFService;
import com.rmn.toolkit.cards.query.service.ReceiptsService;
import com.rmn.toolkit.cards.query.testUtil.EndpointUrlAndConstants;
import com.rmn.toolkit.cards.query.testUtil.RequestDtoBuilder;
import com.rmn.toolkit.cards.query.testUtil.gson.GsonLocalDate;
import com.rmn.toolkit.cards.query.util.ReceiptsUtil;
import com.rmn.toolkit.cards.query.util.ResponseUtil;
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

import java.lang.reflect.Modifier;
import java.time.LocalDate;
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
        CardStatementController.class,
        ReceiptsService.class,
        CreatePDFService.class,
        ReceiptsUtil.class,
        SecurityUtil.class,
        RequestDtoBuilder.class
}, properties = "spring.cloud.config.enabled=false")
@MockBeans({
        @MockBean(ReceiptsService.class),
        @MockBean(CreatePDFService.class),
        @MockBean(ReceiptsUtil.class),
        @MockBean(SecurityUtil.class)
})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CardStatementControllerTest {
    private final MockMvc mockMvc;
    private final RequestDtoBuilder requestDtoBuilder;
    private final ReceiptsService service;
    private final SecurityUtil securityUtil;

    @Test
    @WithMockUser(authorities = "CARD_VIEW")
    public void getCardStatementsWithAuthorization() throws Exception {
        CardStatementsPeriodDto cardStatementsPeriodDto = requestDtoBuilder.createCardStatementsPeriodDto();
        String json = convertRequestDtoToJson(cardStatementsPeriodDto);
        List<ReceiptDto> receipts = new ArrayList<>();

        doNothing().when(securityUtil).checkCurrentClientIdWithCardClientId(anyString());
        when(service.getCardStatements(any(CardStatementsPeriodDto.class), anyInt(), anyInt())).thenReturn(receipts);

        mockMvc.perform(get(EndpointUrlAndConstants.GET_ALL_CARD_STATEMENTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(securityUtil, times(1)).checkCurrentClientIdWithCardClientId(anyString());
        verify(service, times(1)).getCardStatements(any(CardStatementsPeriodDto.class), anyInt(), anyInt());
    }

    @Test
    public void getCardStatementsWithoutAuthorization() throws Exception {
        CardStatementsPeriodDto cardStatementsPeriodDto = requestDtoBuilder.createCardStatementsPeriodDto();
        String json = convertRequestDtoToJson(cardStatementsPeriodDto);

        mockMvc.perform(get(EndpointUrlAndConstants.GET_ALL_CARD_STATEMENTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "CARD_VIEW")
    public void getCardReceiptsAccordingFiltersWithAuthorization() throws Exception {
        CardReceiptsFilters filters = requestDtoBuilder.createCardReceiptsFilters();
        String json = convertRequestDtoToJson(filters);
        List<ReceiptDto> receipts = new ArrayList<>();

        doNothing().when(securityUtil).checkCurrentClientIdWithCardClientId(anyString());
        when(service.getReceipts(any(CardReceiptsFilters.class), anyInt(), anyInt())).thenReturn(receipts);

        mockMvc.perform(get(EndpointUrlAndConstants.GET_ALL_CARD_RECEIPTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(securityUtil, times(1)).checkCurrentClientIdWithCardClientId(anyString());
        verify(service, times(1)).getReceipts(any(CardReceiptsFilters.class), anyInt(), anyInt());
    }

    @Test
    public void getCardReceiptsAccordingFiltersWithoutAuthorization() throws Exception {
        CardReceiptsFilters filters = requestDtoBuilder.createCardReceiptsFilters();
        String json = convertRequestDtoToJson(filters);

        mockMvc.perform(get(EndpointUrlAndConstants.GET_ALL_CARD_RECEIPTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "CARD_VIEW")
    public void searchAllReceiptsByTransactionTypeWithAuthorization() throws Exception {
        TransactionTypeDto transactionTypeDto = requestDtoBuilder.createTransactionTypeDto();
        String json = convertRequestDtoToJson(transactionTypeDto);
        List<ReceiptDto> receipts = new ArrayList<>();

        doNothing().when(securityUtil).checkCurrentClientIdWithCardClientId(anyString());
        when(service.searchAllReceiptsByTransactionType(any(TransactionTypeDto.class), anyInt(), anyInt())).thenReturn(receipts);

        mockMvc.perform(get(EndpointUrlAndConstants.SEARCH_ALL_CARD_RECEIPTS_BY_TRANSACTION_TYPE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(securityUtil, times(1)).checkCurrentClientIdWithCardClientId(anyString());
        verify(service, times(1)).searchAllReceiptsByTransactionType(any(TransactionTypeDto.class), anyInt(), anyInt());
    }

    @Test
    public void searchAllReceiptsByTransactionTypeWithoutAuthorization() throws Exception {
        TransactionTypeDto transactionTypeDto = requestDtoBuilder.createTransactionTypeDto();
        String json = convertRequestDtoToJson(transactionTypeDto);

        mockMvc.perform(get(EndpointUrlAndConstants.SEARCH_ALL_CARD_RECEIPTS_BY_TRANSACTION_TYPE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    private <T> String convertRequestDtoToJson(T requestDto) {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT, Modifier.VOLATILE)
                .registerTypeAdapter(LocalDate.class, new GsonLocalDate());
        Gson gson = builder.create();

        return gson.toJson(requestDto);
    }
}
