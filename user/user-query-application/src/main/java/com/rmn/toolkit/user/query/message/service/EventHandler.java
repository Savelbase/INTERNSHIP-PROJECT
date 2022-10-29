package com.rmn.toolkit.user.query.message.service;

import com.rmn.toolkit.user.query.event.Event;
import com.rmn.toolkit.user.query.event.EventPayload;
import com.rmn.toolkit.user.query.event.client.ApprovedBankClientEvent;
import com.rmn.toolkit.user.query.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.user.query.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.user.query.event.user.NotificationChangeStateEvent;
import com.rmn.toolkit.user.query.event.user.UserDeletedEvent;
import com.rmn.toolkit.user.query.event.user.UserEditedEvent;
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
