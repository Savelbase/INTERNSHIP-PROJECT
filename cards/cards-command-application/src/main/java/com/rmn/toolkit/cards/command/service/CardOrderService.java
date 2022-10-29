package com.rmn.toolkit.cards.command.service;

import com.rmn.toolkit.cards.command.dto.request.CardOrderDto;
import com.rmn.toolkit.cards.command.dto.request.CardOrderStatusDto;
import com.rmn.toolkit.cards.command.event.EventType;
import com.rmn.toolkit.cards.command.event.card.CardOrderCreatedEvent;
import com.rmn.toolkit.cards.command.event.card.CardOrderStatusChangedEvent;
import com.rmn.toolkit.cards.command.message.EventSender;
import com.rmn.toolkit.cards.command.model.CardOrder;
import com.rmn.toolkit.cards.command.util.CardOrderUtil;
import com.rmn.toolkit.cards.command.util.CardProductUtil;
import com.rmn.toolkit.cards.command.util.EventPayloadUtil;
import com.rmn.toolkit.cards.command.util.EventUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardOrderService {
    private final CardProductUtil cardProductUtil;
    private final CardOrderUtil cardOrderUtil;
    private final EventPayloadUtil eventPayloadUtil;
    private final EventUtil eventUtil;
    private final EventSender eventSender;

    @Transactional
    public CardOrder createCardOrder(CardOrderDto cardOrderDto, String clientId) {
        cardProductUtil.findCardProductById(cardOrderDto.getCardProductId());
        CardOrder cardOrder = cardOrderUtil.createCardOrder(cardOrderDto, clientId);

        CardOrderCreatedEvent.Payload payload = eventPayloadUtil.createCardOrderCreatedEventPayload(cardOrder);
        CardOrderCreatedEvent event = CardOrderCreatedEvent.builder().build();
        eventUtil.populateEventFields(event, EventType.CARD_ORDER_CREATED, cardOrder.getId(), cardOrder.getVersion(), clientId, payload);
        eventSender.send(event);
        return cardOrder;
    }

    @Transactional
    public void changeCardOrderStatusById(CardOrder cardOrder, CardOrderStatusDto cardOrderStatusDto, String authorId) {
        cardOrder.setStatus(cardOrderStatusDto.getStatus());

        CardOrderStatusChangedEvent.Payload payload = eventPayloadUtil.createCardOrderStatusChangedEvent(cardOrder);
        CardOrderStatusChangedEvent event = CardOrderStatusChangedEvent.builder().build();
        eventUtil.populateEventFields(event, EventType.CARD_ORDER_STATUS_CHANGED, cardOrder.getId(),
                cardOrder.getVersion() + 1, authorId, payload);
        eventSender.send(event);
    }
}
