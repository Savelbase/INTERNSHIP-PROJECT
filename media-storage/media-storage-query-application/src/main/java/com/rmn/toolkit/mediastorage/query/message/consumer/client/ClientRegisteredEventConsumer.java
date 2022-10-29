package com.rmn.toolkit.mediastorage.query.message.consumer.client;

import com.rmn.toolkit.mediastorage.query.event.Event;
import com.rmn.toolkit.mediastorage.query.event.EventPayload;
import com.rmn.toolkit.mediastorage.query.message.EventConsumer;
import com.rmn.toolkit.mediastorage.query.message.service.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientRegisteredEventConsumer implements EventConsumer {
    private final EventHandler eventHandler;

    @KafkaListener(topics = "${kafka.topic.sign-up}")
    @Override
    public <T extends Event<? extends EventPayload>> void handle(@Payload T event) {
        eventHandler.handle(event);
    }
}
