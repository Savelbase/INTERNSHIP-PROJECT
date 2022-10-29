package com.rmn.toolkit.credits.query.message.service;

import com.rmn.toolkit.credits.query.event.Event;
import com.rmn.toolkit.credits.query.event.EventPayload;
import com.rmn.toolkit.credits.query.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.credits.query.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.credits.query.event.credit.CreditCreatedEvent;
import com.rmn.toolkit.credits.query.event.credit.CreditOrderCreatedEvent;
import com.rmn.toolkit.credits.query.event.credit.CreditOrderStatusChangedEvent;
import com.rmn.toolkit.credits.query.event.user.UserDeletedEvent;
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
            case CREDIT_ORDER_CREATED -> eventService.handle((CreditOrderCreatedEvent) event);
            case CREDIT_ORDER_STATUS_CHANGED -> eventService.handle((CreditOrderStatusChangedEvent) event);
            case CREDIT_CREATED -> eventService.handle((CreditCreatedEvent) event);
        }
    }
}
