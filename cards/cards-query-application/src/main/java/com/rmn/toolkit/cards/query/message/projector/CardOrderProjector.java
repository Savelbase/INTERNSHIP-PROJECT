package com.rmn.toolkit.cards.query.message.projector;

import com.rmn.toolkit.cards.query.event.card.CardOrderCreatedEvent;
import com.rmn.toolkit.cards.query.event.card.CardOrderStatusChangedEvent;
import com.rmn.toolkit.cards.query.model.CardOrder;
import com.rmn.toolkit.cards.query.util.ProjectionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardOrderProjector {
    private static final int VERSION = 1;

    private final ProjectionUtil projectionUtil;

    public CardOrder project(CardOrderCreatedEvent event) {
        var payload = event.getPayload();
        return CardOrder.builder()
                .id(event.getEntityId())
                .clientId(payload.getClientId())
                .cardProductId(payload.getCardProductId())
                .status(payload.getStatus())
                .version(VERSION)
                .build();
    }

    public void project(CardOrderStatusChangedEvent event, CardOrder cardOrder) {
        projectionUtil.validateEvent(event, cardOrder.getId(), cardOrder.getVersion());
        var payload = event.getPayload();
        cardOrder.setStatus(payload.getStatus());
        cardOrder.incrementVersion();
    }
}
