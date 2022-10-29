package com.rmn.toolkit.user.command.message;

import com.rmn.toolkit.user.command.event.Event;
import com.rmn.toolkit.user.command.event.EventPayload;

public interface EventSender {
    void send(Event<? extends EventPayload> event);
}
