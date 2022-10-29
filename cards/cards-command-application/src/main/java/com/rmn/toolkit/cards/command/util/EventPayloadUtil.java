package com.rmn.toolkit.cards.command.util;

import com.rmn.toolkit.cards.command.event.card.CardCreatedEvent;
import com.rmn.toolkit.cards.command.event.card.CardDailyLimitChangedEvent;
import com.rmn.toolkit.cards.command.event.card.CardOrderCreatedEvent;
import com.rmn.toolkit.cards.command.event.card.CardOrderStatusChangedEvent;
import com.rmn.toolkit.cards.command.event.card.CardStatusChangedEvent;
import com.rmn.toolkit.cards.command.model.Account;
import com.rmn.toolkit.cards.command.model.Card;
import com.rmn.toolkit.cards.command.model.CardOrder;
import com.rmn.toolkit.cards.command.model.CardRequisites;
import org.springframework.stereotype.Component;

@Component
public class EventPayloadUtil {

    public CardOrderCreatedEvent.Payload createCardOrderCreatedEventPayload(CardOrder cardOrder){
        return CardOrderCreatedEvent.Payload.builder()
                .clientId(cardOrder.getClientId())
                .cardProductId(cardOrder.getCardProductId())
                .status(cardOrder.getStatus())
                .build();
    }

    public CardOrderStatusChangedEvent.Payload createCardOrderStatusChangedEvent(CardOrder cardOrder) {
        return CardOrderStatusChangedEvent.Payload.builder()
                .status(cardOrder.getStatus())
                .build();
    }

    public CardStatusChangedEvent.Payload createCardStatusChangedEvent(Card card) {
        return CardStatusChangedEvent.Payload.builder()
                .status(card.getStatus())
                .build();
    }

    public CardCreatedEvent.Payload createCardCreatedEvent(Account account, CardRequisites cardRequisites, Card card) {
        return CardCreatedEvent.Payload.builder()
                .account(account)
                .cardRequisites(cardRequisites)
                .cardProductId(card.getCardProductId())
                .cardBalance(card.getCardBalance())
                .dailyLimitSum(card.getDailyLimitSum())
                .status(card.getStatus())
                .build();
    }

    public CardDailyLimitChangedEvent.Payload createCardLimitChangedEvent(Card card) {
        return CardDailyLimitChangedEvent.Payload.builder()
                .dailyLimitSum(card.getDailyLimitSum())
                .build();
    }
}
