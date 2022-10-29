package com.rmn.toolkit.cards.query.model.projection;

import com.rmn.toolkit.cards.query.model.type.Currency;

import java.time.ZonedDateTime;

public interface CardRequisitesView {
    String getId();
    String getCardNumber();
    ZonedDateTime getEndCardPeriod();
    AccountView getAccount();
    ClientView getClient();
    CardProductView getCardProduct();
}
