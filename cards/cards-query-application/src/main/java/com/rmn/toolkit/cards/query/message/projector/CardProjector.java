package com.rmn.toolkit.cards.query.message.projector;

import com.rmn.toolkit.cards.query.event.card.CardCreatedEvent;
import com.rmn.toolkit.cards.query.event.card.CardDailyLimitChangedEvent;
import com.rmn.toolkit.cards.query.event.card.CardStatusChangedEvent;
import com.rmn.toolkit.cards.query.model.Card;
import com.rmn.toolkit.cards.query.model.CardProduct;
import com.rmn.toolkit.cards.query.model.Client;
import com.rmn.toolkit.cards.query.util.CardProductUtil;
import com.rmn.toolkit.cards.query.util.ClientUtil;
import com.rmn.toolkit.cards.query.util.ProjectionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardProjector {
    private static final int VERSION = 1;

    private final ClientUtil clientUtil;
    private final CardProductUtil cardProductUtil;
    private final ProjectionUtil projectionUtil;

    public Card project(CardCreatedEvent event) {
        var payload = event.getPayload();
        Client client = clientUtil.findClientById(event.getAuthorId());
        CardProduct cardProduct = cardProductUtil.findCardProductById(payload.getCardProductId());
        return Card.builder()
                .id(event.getEntityId())
                .cardBalance(payload.getCardBalance())
                .dailyLimitSum(payload.getDailyLimitSum())
                .status(payload.getStatus())
                .client(client)
                .cardProduct(cardProduct)
                .cardRequisites(payload.getCardRequisites())
                .version(VERSION)
                .build();
    }

    public void project(CardStatusChangedEvent event, Card card) {
        projectionUtil.validateEvent(event, card.getId(), card.getVersion());
        var payload = event.getPayload();
        card.setStatus(payload.getStatus());
        card.incrementVersion();
    }

    public void project(CardDailyLimitChangedEvent event, Card card) {
        projectionUtil.validateEvent(event, card.getId(), card.getVersion());
        var payload = event.getPayload();
        card.setDailyLimitSum(payload.getDailyLimitSum());
        card.incrementVersion();
    }
}
