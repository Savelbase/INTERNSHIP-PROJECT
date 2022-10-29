package com.rmn.toolkit.credits.command.message.projector;

import com.rmn.toolkit.credits.command.event.credit.CreditOrderCreatedEvent;
import com.rmn.toolkit.credits.command.event.credit.CreditOrderStatusChangedEvent;
import com.rmn.toolkit.credits.command.model.CreditOrder;
import com.rmn.toolkit.credits.command.util.ProjectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreditOrderProjector {
    private static final int VERSION = 1;

    private final ProjectionUtil projectionUtil;

    public CreditOrder project(CreditOrderCreatedEvent event) {
        var payload = event.getPayload();
        return CreditOrder.builder()
                .id(event.getEntityId())
                .creditAmount(payload.getCreditAmount())
                .startCreditPeriod(payload.getStartCreditPeriod())
                .endCreditPeriod(payload.getEndCreditPeriod())
                .averageMonthlyIncome(payload.getAverageMonthlyIncome())
                .averageMonthlyExpenses(payload.getAverageMonthlyExpenses())
                .employerTin(payload.getEmployerTin())
                .status(payload.getStatus())
                .clientId(event.getAuthorId())
                .creditProductName(payload.getCreditProductName())
                .version(VERSION)
                .build();
    }

    public void project(CreditOrderStatusChangedEvent event, CreditOrder creditOrder) {
        projectionUtil.validateEvent(event, creditOrder.getId(), creditOrder.getVersion());
        var payload =  event.getPayload();
        creditOrder.setStatus(payload.getStatus());
        creditOrder.incrementVersion();
    }
}

