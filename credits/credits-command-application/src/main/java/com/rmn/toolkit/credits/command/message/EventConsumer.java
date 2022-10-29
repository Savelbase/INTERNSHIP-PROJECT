package com.rmn.toolkit.credits.command.message;

import com.rmn.toolkit.credits.command.event.Event;
import com.rmn.toolkit.credits.command.event.EventPayload;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    <T extends Event<? extends EventPayload>> void handle(@Payload T event);
}