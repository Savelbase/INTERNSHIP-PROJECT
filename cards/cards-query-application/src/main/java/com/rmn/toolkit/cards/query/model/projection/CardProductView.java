package com.rmn.toolkit.cards.query.model.projection;

import com.rmn.toolkit.cards.query.model.CardConditions;
import com.rmn.toolkit.cards.query.model.CardConditionsDictionary;
import com.rmn.toolkit.cards.query.model.type.CardType;
import com.rmn.toolkit.cards.query.model.type.Currency;

public interface CardProductView {
    String getCardName();
    CardType getType();
    Currency getCurrency();
    CardConditionsDictionary getCardConditionsDictionary();
    CardConditions getCardConditions();
}
