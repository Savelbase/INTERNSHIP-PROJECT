package com.rmn.toolkit.authorization.message.service;

import com.rmn.toolkit.authorization.event.Event;
import com.rmn.toolkit.authorization.event.EventPayload;
import com.rmn.toolkit.authorization.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.authorization.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.authorization.event.user.UserDeletedEvent;
import com.rmn.toolkit.authorization.event.user.UserEditedEvent;
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
            case USER_EDITED -> eventService.handle((UserEditedEvent) event);
            case USER_DELETED -> eventService.handle((UserDeletedEvent) event);
        }
    }
}
