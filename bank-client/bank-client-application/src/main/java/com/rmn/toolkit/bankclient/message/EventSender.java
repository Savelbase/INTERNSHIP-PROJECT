package com.rmn.toolkit.bankclient.message;

import com.rmn.toolkit.bankclient.event.Event;
import com.rmn.toolkit.bankclient.event.EventPayload;

public interface EventSender {
    void send(Event<? extends EventPayload> event);
}
