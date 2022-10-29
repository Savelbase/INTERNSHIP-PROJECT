package com.rmn.toolkit.user.query.message;

import com.rmn.toolkit.user.query.event.Event;
import com.rmn.toolkit.user.query.event.EventPayload;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    <T extends Event<? extends EventPayload>> void handle(@Payload T event);
}
