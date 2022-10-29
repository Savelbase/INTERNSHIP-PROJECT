package com.rmn.toolkit.cards.query.testUtil;

import com.rmn.toolkit.cards.query.dto.request.CardReceiptsFilters;
import com.rmn.toolkit.cards.query.dto.request.CardStatementsPeriodDto;
import com.rmn.toolkit.cards.query.dto.request.TransactionTypeDto;
import com.rmn.toolkit.cards.query.dto.response.success.CardOrderDto;
import com.rmn.toolkit.cards.query.model.type.PaymentOperationType;
import com.rmn.toolkit.cards.query.model.type.TransactionType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class RequestDtoBuilder {
    private static final double MIN_SUM = 00.01;
    private static final int MAX_SUM = 1000000;

    public CardStatementsPeriodDto createCardStatementsPeriodDto() {
        return CardStatementsPeriodDto.builder()
                .cardId(EndpointUrlAndConstants.TEST_ID)
                .startPeriod(LocalDate.now())
                .endPeriod(LocalDate.now())
                .build();
    }

    public CardReceiptsFilters createCardReceiptsFilters() {
        return CardReceiptsFilters.builder()
                .cardId(EndpointUrlAndConstants.TEST_ID)
                .operationType(PaymentOperationType.ALL)
                .transactionType(TransactionType.ALL)
                .minOperationSum(BigDecimal.valueOf(MIN_SUM))
                .maxOperationSum(BigDecimal.valueOf(MAX_SUM))
                .startPeriod(LocalDate.now())
                .endPeriod(LocalDate.now())
                .build();
    }

    public TransactionTypeDto createTransactionTypeDto() {
        return TransactionTypeDto.builder()
                .cardId(EndpointUrlAndConstants.TEST_ID)
                .transactionType("all")
                .build();
    }

    public CardOrderDto createCardOrderDto() {
        return CardOrderDto.builder().build();
    }
}
