package com.rmn.toolkit.cards.command.message;

import com.rmn.toolkit.cards.command.event.Event;
import com.rmn.toolkit.cards.command.event.EventPayload;

public interface EventSender {
    void send(Event<? extends EventPayload> event);
}