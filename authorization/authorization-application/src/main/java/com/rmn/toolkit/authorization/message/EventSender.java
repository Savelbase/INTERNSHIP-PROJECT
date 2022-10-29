package com.rmn.toolkit.authorization.message;

import com.rmn.toolkit.authorization.event.Event;
import com.rmn.toolkit.authorization.event.EventPayload;

public interface EventSender {
    void send(Event<? extends EventPayload> event);
}
