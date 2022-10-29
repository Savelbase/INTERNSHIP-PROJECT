package com.rmn.toolkit.credits.query.message.projector;

import com.rmn.toolkit.credits.query.event.credit.CreditCreatedEvent;
import com.rmn.toolkit.credits.query.model.Credit;
import com.rmn.toolkit.credits.query.model.CreditDictionary;
import com.rmn.toolkit.credits.query.model.PayGraph;
import com.rmn.toolkit.credits.query.util.CreditDictionaryUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreditProjector {
    private static final int VERSION = 1;

    private final CreditDictionaryUtil creditDictionaryUtil;

    public Credit project(CreditCreatedEvent event, PayGraph payGraph) {
        var payload = event.getPayload();
        CreditDictionary creditProduct = creditDictionaryUtil.findCreditProductById(payload.getCreditProductId());
        return Credit.builder()
                .id(event.getEntityId())
                .agreementNumber(payload.getAgreementNumber())
                .creditAmount(payload.getCreditAmount())
                .debt(payload.getDebt())
                .startCreditPeriod(payload.getStartCreditPeriod())
                .endCreditPeriod(payload.getEndCreditPeriod())
                .dateToPay(payload.getDateToPay())
                .employerTin(payload.getEmployerTin())
                .creditProduct(creditProduct)
                .account(payload.getAccount())
                .payGraph(payGraph)
                .version(VERSION)
                .build();
    }
}
