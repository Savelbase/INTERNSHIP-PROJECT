package com.rmn.toolkit.mediastorage.command.message;

import com.rmn.toolkit.mediastorage.command.event.Event;
import com.rmn.toolkit.mediastorage.command.event.EventPayload;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    <T extends Event<? extends EventPayload>> void handle(@Payload T event);
}
