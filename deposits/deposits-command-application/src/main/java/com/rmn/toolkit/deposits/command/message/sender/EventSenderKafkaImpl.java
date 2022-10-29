package com.rmn.toolkit.deposits.command.message.sender;


import com.rmn.toolkit.deposits.command.event.Event;
import com.rmn.toolkit.deposits.command.event.EventPayload;
import com.rmn.toolkit.deposits.command.message.EventSender;
import com.rmn.toolkit.deposits.command.repository.EventRepository;
import com.rmn.toolkit.deposits.command.util.EventUtil;
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
    @Value("${kafka.topic.deposit}")
    private String depositTopic;

    @Override
    public void send(Event<? extends EventPayload> event) {
        kafkaTemplate.send(depositTopic, EventUtil.uuidStringToLong(event.getEntityId()), event);
        log.info("kafka send deposit event {} with entity-id {}", event.getType(), event.getEntityId());
        eventRepository.save(event);
        kafkaTemplate.flush();
    }
}
