package com.rmn.toolkit.authorization.message.sender;

import com.rmn.toolkit.authorization.event.Event;
import com.rmn.toolkit.authorization.event.EventPayload;
import com.rmn.toolkit.authorization.message.EventSender;
import com.rmn.toolkit.authorization.repository.EventRepository;
import com.rmn.toolkit.authorization.util.EventUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventSenderKafkaImpl implements EventSender {
    @Value("${kafka.topic.client-status}")
    private String clientStatusTopic;
    private final KafkaTemplate<Long, Event<? extends EventPayload>> kafkaTemplate;
    private final EventRepository eventRepository;

    @Override
    public void send(Event<? extends EventPayload> event) {
        kafkaTemplate.send(clientStatusTopic, EventUtil.uuidStringToLong(event.getEntityId()), event);
        log.info("kafka send 'client-blocked' event with entity-id {}" , event.getEntityId());
        eventRepository.save(event);
        kafkaTemplate.flush();
    }
}
