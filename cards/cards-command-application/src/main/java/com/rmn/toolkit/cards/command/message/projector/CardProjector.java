package com.rmn.toolkit.cards.command.message.projector;

import com.rmn.toolkit.cards.command.event.card.CardCreatedEvent;
import com.rmn.toolkit.cards.command.event.card.CardDailyLimitChangedEvent;
import com.rmn.toolkit.cards.command.event.card.CardStatusChangedEvent;
import com.rmn.toolkit.cards.command.model.Card;
import com.rmn.toolkit.cards.command.util.ProjectionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardProjector {
    private static final int VERSION = 1;

    private final ProjectionUtil projectionUtil;

    public Card project(CardCreatedEvent event) {
        var payload = event.getPayload();
        return Card.builder()
                .id(event.getEntityId())
                .clientId(event.getAuthorId())
                .cardProductId(payload.getCardProductId())
                .cardRequisitesId(payload.getCardRequisites().getId())
                .cardBalance(payload.getCardBalance())
                .dailyLimitSum(payload.getDailyLimitSum())
                .status(payload.getStatus())
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
