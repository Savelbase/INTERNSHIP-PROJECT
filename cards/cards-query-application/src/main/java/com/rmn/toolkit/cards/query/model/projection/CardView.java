package com.rmn.toolkit.cards.query.model.projection;

import com.rmn.toolkit.cards.query.model.type.CardStatusType;
import com.rmn.toolkit.cards.query.model.type.CardType;

import java.math.BigDecimal;

public interface CardView {
    String getId();
    CardStatusType getStatus();
    BigDecimal getCardBalance();
    ClientView getClient();
    CardProductView getCardProduct();
    CardRequisitesView getCardRequisites();
}
