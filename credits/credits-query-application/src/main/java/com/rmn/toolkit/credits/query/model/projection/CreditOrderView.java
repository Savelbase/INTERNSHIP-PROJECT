package com.rmn.toolkit.credits.query.model.projection;

import com.rmn.toolkit.credits.query.model.type.CreditOrderStatusType;

import java.math.BigDecimal;

public interface CreditOrderView {
    String getId();
    CreditOrderStatusType getStatus();
    BigDecimal getCreditAmount();
    CreditDictionaryView getCreditProduct();
}
