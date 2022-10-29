package com.rmn.toolkit.cards.query.model.projection;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public interface ReceiptView {
    String getId();
    String getTransactionType();
    ZonedDateTime getTransactionTime();
    BigDecimal getTransactionAmount();
    String getTransactionLocation();
    String getAdditionalInfo();
    CardView getCard();
    RecipientView getRecipient();
}
