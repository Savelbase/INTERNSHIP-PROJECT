package com.rmn.toolkit.user.registration.message;

import com.rmn.toolkit.user.registration.event.Event;
import com.rmn.toolkit.user.registration.event.EventPayload;

public interface EventSender {
    void send(Event<? extends EventPayload> event);
}
