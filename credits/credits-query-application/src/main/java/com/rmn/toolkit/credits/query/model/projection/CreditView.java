package com.rmn.toolkit.credits.query.model.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface CreditView {
    String getId();
    BigDecimal getCreditAmount();
    BigDecimal getDebt();
    String getAgreementNumber();
    LocalDate getStartCreditPeriod();
    LocalDate getEndCreditPeriod();
    LocalDate getDateToPay();
    CreditDictionaryView getCreditProduct();
    AccountView getAccount();
    PayGraphView getPayGraph();
}
