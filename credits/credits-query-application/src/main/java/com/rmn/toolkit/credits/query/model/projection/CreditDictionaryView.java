package com.rmn.toolkit.credits.query.model.projection;

import com.rmn.toolkit.credits.query.model.type.Currency;

public interface CreditDictionaryView {
    String getName();
    Currency getCurrency();
    Double getPercent();
}
