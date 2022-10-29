package com.rmn.toolkit.credits.query.message.projector;

import com.rmn.toolkit.credits.query.event.credit.CreditOrderCreatedEvent;
import com.rmn.toolkit.credits.query.event.credit.CreditOrderStatusChangedEvent;
import com.rmn.toolkit.credits.query.model.CreditDictionary;
import com.rmn.toolkit.credits.query.model.CreditOrder;
import com.rmn.toolkit.credits.query.util.CreditDictionaryUtil;
import com.rmn.toolkit.credits.query.util.ProjectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreditOrderProjector {
    private static final int VERSION = 1;

    private final ProjectionUtil projectionUtil;
    private final CreditDictionaryUtil creditDictionaryUtil;

    public CreditOrder project(CreditOrderCreatedEvent event) {
        var payload = event.getPayload();
        CreditDictionary creditProduct = creditDictionaryUtil.findCreditProductByName(payload.getCreditProductName());
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
                .creditProduct(creditProduct)
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

