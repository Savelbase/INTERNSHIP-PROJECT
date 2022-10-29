package com.rmn.toolkit.cards.query.message.service;

import com.rmn.toolkit.cards.query.event.Event;
import com.rmn.toolkit.cards.query.event.EventPayload;
import com.rmn.toolkit.cards.query.event.card.*;
import com.rmn.toolkit.cards.query.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.cards.query.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.cards.query.event.user.UserDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventHandler {
    private final EventService eventService;

    public void handle(Event<? extends EventPayload> event) {
        switch (event.getType()) {
            case CLIENT_REGISTERED -> eventService.handle((ClientRegisteredEvent) event);
            case CLIENT_STATUS_CHANGED -> eventService.handle((ClientStatusChangedEvent) event);
            case USER_DELETED -> eventService.handle((UserDeletedEvent) event);
            case CARD_ORDER_CREATED -> eventService.handle((CardOrderCreatedEvent) event);
            case CARD_ORDER_STATUS_CHANGED -> eventService.handle((CardOrderStatusChangedEvent) event);
            case CARD_CREATED -> eventService.handle((CardCreatedEvent) event);
            case CARD_STATUS_CHANGED -> eventService.handle((CardStatusChangedEvent) event);
            case CARD_DAILY_LIMIT_CHANGED -> eventService.handle((CardDailyLimitChangedEvent) event);
        }
    }
}
