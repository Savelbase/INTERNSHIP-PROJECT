package com.rmn.toolkit.cards.command.event.card;

import com.rmn.toolkit.cards.command.event.Event;
import com.rmn.toolkit.cards.command.event.EventPayload;
import com.rmn.toolkit.cards.command.model.Account;
import com.rmn.toolkit.cards.command.model.CardRequisites;
import com.rmn.toolkit.cards.command.model.type.CardStatusType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue(value = "CARD_CREATED")
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
