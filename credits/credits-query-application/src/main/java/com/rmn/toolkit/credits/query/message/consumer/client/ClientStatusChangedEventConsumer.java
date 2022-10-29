package com.rmn.toolkit.credits.query.message.consumer.client;

import com.rmn.toolkit.credits.query.event.Event;
import com.rmn.toolkit.credits.query.event.EventPayload;
import com.rmn.toolkit.credits.query.message.EventConsumer;
import com.rmn.toolkit.credits.query.message.service.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientStatusChangedEventConsumer implements EventConsumer {
    private final EventHandler eventHandler;

    @KafkaListener(topics = "${kafka.topic.client-status}")
    @Override
    public <T extends Event<? extends EventPayload>> void handle(@Payload T event) {
        eventHandler.handle(event);
    }
}
