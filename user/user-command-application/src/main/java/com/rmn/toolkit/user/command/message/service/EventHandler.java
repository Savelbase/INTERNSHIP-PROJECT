package com.rmn.toolkit.user.command.message.service;

import com.rmn.toolkit.user.command.event.Event;
import com.rmn.toolkit.user.command.event.EventPayload;
import com.rmn.toolkit.user.command.event.client.ApprovedBankClientEvent;
import com.rmn.toolkit.user.command.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.user.command.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.user.command.event.user.NotificationChangeStateEvent;
import com.rmn.toolkit.user.command.event.user.UserDeletedEvent;
import com.rmn.toolkit.user.command.event.user.UserEditedEvent;
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
            case APPROVED_BANK_CLIENT -> eventService.handle((ApprovedBankClientEvent) event);
            case USER_EDITED -> eventService.handle((UserEditedEvent) event);
            case USER_DELETED -> eventService.handle((UserDeletedEvent) event);
            case NOTIFICATION_CHANGE_STATE -> eventService.handle((NotificationChangeStateEvent) event);
        }
    }
}
