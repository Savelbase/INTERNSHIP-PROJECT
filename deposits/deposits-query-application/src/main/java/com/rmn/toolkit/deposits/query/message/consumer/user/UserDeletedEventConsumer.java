package com.rmn.toolkit.deposits.query.message.consumer.user;

import com.rmn.toolkit.deposits.query.event.Event;
import com.rmn.toolkit.deposits.query.event.EventPayload;
import com.rmn.toolkit.deposits.query.message.EventConsumer;
import com.rmn.toolkit.deposits.query.message.service.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDeletedEventConsumer implements EventConsumer {
    private final EventHandler eventHandler;

    @KafkaListener(topics = "${kafka.topic.user-delete}")
    @Override
    public <T extends Event<? extends EventPayload>> void handle(@Payload T event) {
        eventHandler.handle(event);
    }
}
