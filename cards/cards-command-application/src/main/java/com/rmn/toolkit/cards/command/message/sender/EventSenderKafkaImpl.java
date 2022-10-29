package com.rmn.toolkit.cards.command.message.sender;

import com.rmn.toolkit.cards.command.event.Event;
import com.rmn.toolkit.cards.command.event.EventPayload;
import com.rmn.toolkit.cards.command.message.EventSender;
import com.rmn.toolkit.cards.command.repository.EventRepository;
import com.rmn.toolkit.cards.command.util.EventUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventSenderKafkaImpl implements EventSender {
    private final EventRepository eventRepository;
    private final KafkaTemplate<Long, Event<? extends EventPayload>> kafkaTemplate;
    @Value("${kafka.topic.card}")
    private String cardTopic;

    @Override
    public void send(Event<? extends EventPayload> event) {
        kafkaTemplate.send(cardTopic, EventUtil.uuidStringToLong(event.getEntityId()), event);
        log.info("kafka send card event {} with entity-id {}", event.getType(), event.getEntityId());
        eventRepository.save(event);
        kafkaTemplate.flush();
    }
}
