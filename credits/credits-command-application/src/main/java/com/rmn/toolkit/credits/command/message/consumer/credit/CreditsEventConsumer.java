package com.rmn.toolkit.credits.command.message.consumer.credit;

import com.rmn.toolkit.credits.command.event.Event;
import com.rmn.toolkit.credits.command.event.EventPayload;
import com.rmn.toolkit.credits.command.message.EventConsumer;
import com.rmn.toolkit.credits.command.message.service.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreditsEventConsumer implements EventConsumer {
    private final EventHandler eventHandler;

    @KafkaListener(topics = "${kafka.topic.credit}")
    @Override
    public <T extends Event<? extends EventPayload>> void handle(@Payload T event) {
        eventHandler.handle(event);
    }
}
