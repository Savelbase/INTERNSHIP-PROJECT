package com.rmn.toolkit.mediastorage.command.message.service;

import com.rmn.toolkit.mediastorage.command.event.Event;
import com.rmn.toolkit.mediastorage.command.event.EventPayload;
import com.rmn.toolkit.mediastorage.command.event.avatar.AvatarUploadedEvent;
import com.rmn.toolkit.mediastorage.command.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.mediastorage.command.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.mediastorage.command.event.user.UserDeletedEvent;
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
            case AVATAR_UPLOADED -> eventService.handle((AvatarUploadedEvent) event);
        }
    }
}
