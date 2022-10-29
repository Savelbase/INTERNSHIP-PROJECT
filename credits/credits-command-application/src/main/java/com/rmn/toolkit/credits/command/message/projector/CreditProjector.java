package com.rmn.toolkit.credits.command.message.projector;

import com.rmn.toolkit.credits.command.event.credit.CreditCreatedEvent;
import com.rmn.toolkit.credits.command.model.Credit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreditProjector {
    private static final int VERSION = 1;

    public Credit project(CreditCreatedEvent event) {
        var payload = event.getPayload();
        return Credit.builder()
                .id(event.getEntityId())
                .accountId(payload.getAccount().getId())
                .creditProductId(payload.getCreditProductId())
                .agreementNumber(payload.getAgreementNumber())
                .creditAmount(payload.getCreditAmount())
                .debt(payload.getDebt())
                .startCreditPeriod(payload.getStartCreditPeriod())
                .endCreditPeriod(payload.getEndCreditPeriod())
                .dateToPay(payload.getDateToPay())
                .employerTin(payload.getEmployerTin())
                .version(VERSION)
                .build();
    }
}
