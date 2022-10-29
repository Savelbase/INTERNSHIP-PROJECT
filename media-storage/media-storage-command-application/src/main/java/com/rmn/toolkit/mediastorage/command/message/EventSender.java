package com.rmn.toolkit.mediastorage.command.message;

import com.rmn.toolkit.mediastorage.command.event.Event;
import com.rmn.toolkit.mediastorage.command.event.EventPayload;

public interface EventSender {
    void send(Event<? extends EventPayload> event);
}
