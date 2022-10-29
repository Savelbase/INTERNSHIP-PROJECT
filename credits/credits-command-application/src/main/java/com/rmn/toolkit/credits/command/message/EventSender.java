package com.rmn.toolkit.credits.command.message;

import com.rmn.toolkit.credits.command.event.Event;
import com.rmn.toolkit.credits.command.event.EventPayload;

public interface EventSender {
    void send(Event<? extends EventPayload> event);
}