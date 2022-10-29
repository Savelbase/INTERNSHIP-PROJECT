package com.rmn.toolkit.user.registration.message.service;

import com.rmn.toolkit.user.registration.event.Event;
import com.rmn.toolkit.user.registration.event.EventPayload;
import com.rmn.toolkit.user.registration.event.client.BankClientCreatedEvent;
import com.rmn.toolkit.user.registration.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.user.registration.event.user.UserDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventHandler {
    private final EventService eventService;

    public void handle(Event<? extends EventPayload> event) {
        switch (event.getType()) {
            case BANK_CLIENT_CREATED -> eventService.handle((BankClientCreatedEvent) event);
            case CLIENT_REGISTERED -> eventService.handle((ClientRegisteredEvent) event);
            case USER_DELETED -> eventService.handle((UserDeletedEvent) event);
        }
    }
}
