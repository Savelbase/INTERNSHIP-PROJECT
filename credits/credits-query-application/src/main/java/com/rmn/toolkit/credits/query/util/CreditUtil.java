package com.rmn.toolkit.credits.query.util;

import com.rmn.toolkit.credits.query.dto.response.success.CreditDto;
import com.rmn.toolkit.credits.query.model.projection.AccountView;
import com.rmn.toolkit.credits.query.model.projection.CreditDictionaryView;
import com.rmn.toolkit.credits.query.model.projection.CreditView;
import com.rmn.toolkit.credits.query.model.projection.PayGraphView;
import org.springframework.stereotype.Component;

@Component
public class CreditUtil {

    public CreditDto createCreditDto(CreditView credit) {
        CreditDictionaryView creditProduct = credit.getCreditProduct();
        AccountView account = credit.getAccount();
        PayGraphView payGraph = credit.getPayGraph();
        return CreditDto.builder()
                .id(credit.getId())
                .creditProductName(creditProduct.getName())
                .creditAmount(credit.getCreditAmount())
                .currency(creditProduct.getCurrency())
                .percent(creditProduct.getPercent())
                .debt(credit.getDebt())
                .agreementNumber(credit.getAgreementNumber())
                .accountNumber(account.getAccountNumber())
                .startCreditPeriod(credit.getStartCreditPeriod())
                .endCreditPeriod(credit.getEndCreditPeriod())
                .dateToPay(credit.getDateToPay())
                .paymentList(payGraph.getPaymentList())
                .build();
    }
}
