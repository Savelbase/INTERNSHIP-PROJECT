package com.rmn.toolkit.cards.query.event.card;

import com.rmn.toolkit.cards.query.event.Event;
import com.rmn.toolkit.cards.query.event.EventPayload;
import com.rmn.toolkit.cards.query.model.Account;
import com.rmn.toolkit.cards.query.model.CardRequisites;
import com.rmn.toolkit.cards.query.model.type.CardStatusType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CardCreatedEvent extends Event<CardCreatedEvent.Payload> {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = true)
    public static class Payload extends EventPayload {
        private Account account;
        private CardRequisites cardRequisites;
        private String cardProductId;
        private BigDecimal cardBalance;
        private BigDecimal dailyLimitSum;
        private CardStatusType status;
    }
}
