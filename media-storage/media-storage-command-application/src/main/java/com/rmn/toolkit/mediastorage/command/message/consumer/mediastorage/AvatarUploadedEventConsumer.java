package com.rmn.toolkit.mediastorage.command.message.consumer.mediastorage;

import com.rmn.toolkit.mediastorage.command.event.Event;
import com.rmn.toolkit.mediastorage.command.event.EventPayload;
import com.rmn.toolkit.mediastorage.command.message.EventConsumer;
import com.rmn.toolkit.mediastorage.command.message.service.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AvatarUploadedEventConsumer implements EventConsumer {
    private final EventHandler eventHandler;

    @KafkaListener(topics = "${kafka.topic.media-storage}")
    @Override
    public <T extends Event<? extends EventPayload>> void handle(@Payload T event) {
        eventHandler.handle(event);
    }
}
