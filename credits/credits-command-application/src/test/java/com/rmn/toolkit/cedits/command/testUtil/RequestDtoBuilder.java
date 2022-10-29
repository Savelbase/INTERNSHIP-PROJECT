package com.rmn.toolkit.cedits.command.testUtil;

import com.rmn.toolkit.credits.command.dto.request.CreditOrderDto;
import com.rmn.toolkit.credits.command.dto.request.CreditOrderStatusDto;
import com.rmn.toolkit.credits.command.model.CreditOrder;
import com.rmn.toolkit.credits.command.model.type.CreditOrderStatusType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RequestDtoBuilder {

    public CreditOrderDto createCreditOrderDto() {
        return CreditOrderDto.builder()
                .creditAmount(BigDecimal.ONE)
                .monthPeriod(EndpointUrlAndConstants.TEST_INT_VALUE)
                .averageMonthlyIncome(BigDecimal.ONE)
                .averageMonthlyExpenses(BigDecimal.ONE)
                .employerTin(EndpointUrlAndConstants.TEST_VALUE)
                .creditProductName(EndpointUrlAndConstants.TEST_VALUE)
                .build();
    }

    public CreditOrderStatusDto createCreditOrderStatusDto() {
        return CreditOrderStatusDto.builder()
                .creditOrderId(EndpointUrlAndConstants.TEST_VALUE)
                .statusType(CreditOrderStatusType.APPROVED)
                .build();
    }

    public CreditOrder createCreditOrder() {
        return CreditOrder.builder()
                .id(EndpointUrlAndConstants.TEST_VALUE)
                .status(CreditOrderStatusType.PROCESSING)
                .build();
    }
}
