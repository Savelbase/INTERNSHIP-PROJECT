package com.rmn.toolkit.credits.command.util;

import com.rmn.toolkit.credits.command.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class CreditUtil {

    public long getMonthCount(LocalDate start, LocalDate end) {
        return ChronoUnit.MONTHS.between(YearMonth.from(start), YearMonth.from(end));
    }

    public Credit createCredit(CreditOrder creditOrder, String accountId, String creditProductId) {
        return Credit.builder()
                .id(UUID.randomUUID().toString())
                .accountId(accountId)
                .creditProductId(creditProductId)
                .agreementNumber(UUID.randomUUID().toString())
                .creditAmount(creditOrder.getCreditAmount())
                .debt(creditOrder.getCreditAmount())
                .startCreditPeriod(creditOrder.getStartCreditPeriod())
                .endCreditPeriod(creditOrder.getEndCreditPeriod())
                .dateToPay(creditOrder.getStartCreditPeriod().plusMonths(1))
                .employerTin(creditOrder.getEmployerTin())
                .build();
    }

    public Account createAccount(String clientId) {
        return Account.builder()
                .id(UUID.randomUUID().toString())
                .clientId(clientId)
                .accountNumber(UUID.randomUUID().toString())
                .build();
    }

    public PayGraph createPayGraphWithoutPaymentList(String creditId) {
        return PayGraph.builder()
                .id(UUID.randomUUID().toString())
                .creditId(creditId)
                .build();
    }
}
