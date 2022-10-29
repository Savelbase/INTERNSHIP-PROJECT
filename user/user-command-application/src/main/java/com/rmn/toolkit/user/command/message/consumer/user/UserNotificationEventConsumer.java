package com.rmn.toolkit.user.command.message.consumer.user;

import com.rmn.toolkit.user.command.event.Event;
import com.rmn.toolkit.user.command.event.EventPayload;
import com.rmn.toolkit.user.command.message.EventConsumer;
import com.rmn.toolkit.user.command.message.service.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserNotificationEventConsumer implements EventConsumer {
    private final EventHandler eventHandler;

    @KafkaListener(topics = "${kafka.topic.notification}")
    @Override
    public <T extends Event<? extends EventPayload>> void handle(@Payload T event) {
        eventHandler.handle(event);
    }
}
