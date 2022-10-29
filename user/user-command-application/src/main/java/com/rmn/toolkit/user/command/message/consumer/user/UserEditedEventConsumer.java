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
public class UserEditedEventConsumer implements EventConsumer {
    private final EventHandler eventHandler;

    @KafkaListener(topics = "${kafka.topic.user-edit}")
    @Override
    public <T extends Event<? extends EventPayload>> void handle(@Payload T event) {
        eventHandler.handle(event);
    }
}