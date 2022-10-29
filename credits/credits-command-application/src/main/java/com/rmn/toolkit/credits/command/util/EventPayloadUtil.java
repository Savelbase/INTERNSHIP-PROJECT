package com.rmn.toolkit.credits.command.util;

import com.rmn.toolkit.credits.command.event.credit.CreditCreatedEvent;
import com.rmn.toolkit.credits.command.event.credit.CreditOrderCreatedEvent;
import com.rmn.toolkit.credits.command.event.credit.CreditOrderStatusChangedEvent;
import com.rmn.toolkit.credits.command.model.Account;
import com.rmn.toolkit.credits.command.model.Credit;
import com.rmn.toolkit.credits.command.model.CreditOrder;
import com.rmn.toolkit.credits.command.model.PayGraph;
import org.springframework.stereotype.Component;

@Component
public class EventPayloadUtil {

    public CreditOrderCreatedEvent.Payload createCreditOrderCreatedEventPayload(CreditOrder creditOrder){
        return CreditOrderCreatedEvent.Payload.builder()
                .creditAmount(creditOrder.getCreditAmount())
                .startCreditPeriod(creditOrder.getStartCreditPeriod())
                .endCreditPeriod(creditOrder.getEndCreditPeriod())
                .averageMonthlyIncome(creditOrder.getAverageMonthlyIncome())
                .averageMonthlyExpenses(creditOrder.getAverageMonthlyExpenses())
                .employerTin(creditOrder.getEmployerTin())
                .status(creditOrder.getStatus())
                .creditProductName(creditOrder.getCreditProductName())
                .build();
    }

    public CreditOrderStatusChangedEvent.Payload createCreditOrderStatusChangedEvent(CreditOrder creditOrder){
        return CreditOrderStatusChangedEvent.Payload.builder()
                .status(creditOrder.getStatus())
                .build();
    }

    public CreditCreatedEvent.Payload createCreditCreatedEvent(Credit credit, Account account, PayGraph payGraph) {
        return CreditCreatedEvent.Payload.builder()
                .account(account)
                .creditProductId(credit.getCreditProductId())
                .agreementNumber(credit.getAgreementNumber())
                .creditAmount(credit.getCreditAmount())
                .debt(credit.getDebt())
                .startCreditPeriod(credit.getStartCreditPeriod())
                .endCreditPeriod(credit.getEndCreditPeriod())
                .dateToPay(credit.getDateToPay())
                .employerTin(credit.getEmployerTin())
                .payGraphId(payGraph.getId())
                .paymentList(payGraph.getPaymentList())
                .build();
    }
}
