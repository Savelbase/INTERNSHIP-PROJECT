package com.rmn.toolkit.deposits.command.message.service;


import com.rmn.toolkit.deposits.command.event.Event;
import com.rmn.toolkit.deposits.command.event.EventPayload;
import com.rmn.toolkit.deposits.command.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.deposits.command.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.deposits.command.event.deposit.DepositCreatedEvent;
import com.rmn.toolkit.deposits.command.event.user.UserDeletedEvent;
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
            case DEPOSIT_CREATED -> eventService.handle((DepositCreatedEvent) event);
        }
    }
}
