package com.rmn.toolkit.user.registration.message.sender;

import com.rmn.toolkit.user.registration.event.Event;
import com.rmn.toolkit.user.registration.event.EventPayload;
import com.rmn.toolkit.user.registration.message.EventSender;
import com.rmn.toolkit.user.registration.repository.EventRepository;
import com.rmn.toolkit.user.registration.util.EventUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventSenderKafkaImpl implements EventSender {
    @Value("${kafka.topic.sign-up}")
    private String signUpTopic;
    private final KafkaTemplate<Long, Event<? extends EventPayload>> kafkaTemplate;
    private final EventRepository eventRepository;

    @Override
    public void send(Event<? extends EventPayload> event) {
        kafkaTemplate.send(signUpTopic, EventUtil.uuidStringToLong(event.getEntityId()), event);
        log.info("kafka send 'sign-up' event with entity-id {}", event.getEntityId());
        eventRepository.save(event);
        kafkaTemplate.flush();
    }
}
