package com.rmn.toolkit.credits.command.message.sender;

import com.rmn.toolkit.credits.command.repository.EventRepository;
import com.rmn.toolkit.credits.command.util.EventUtil;
import com.rmn.toolkit.credits.command.event.Event;
import com.rmn.toolkit.credits.command.event.EventPayload;
import com.rmn.toolkit.credits.command.message.EventSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventSenderKafkaImpl implements EventSender {
    @Value("${kafka.topic.credit}")
    private String creditTopic;
    private final KafkaTemplate<Long, Event<? extends EventPayload>> kafkaTemplate;
    private final EventRepository eventRepository;

    @Override
    public void send(Event<? extends EventPayload> event) {
        kafkaTemplate.send(creditTopic, EventUtil.uuidStringToLong(event.getEntityId()), event);
        log.info("kafka send {} event with entity-id {}", event.getType().name() , event.getEntityId());
        eventRepository.save(event);
        kafkaTemplate.flush();
    }
}

