package com.rmn.toolkit.mediastorage.command.message.sender;

import com.rmn.toolkit.mediastorage.command.event.Event;
import com.rmn.toolkit.mediastorage.command.message.EventSender;
import com.rmn.toolkit.mediastorage.command.event.EventPayload;
import com.rmn.toolkit.mediastorage.command.repository.EventRepository;
import com.rmn.toolkit.mediastorage.command.util.EventUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventSenderKafkaImpl implements EventSender {
    @Value("${kafka.topic.media-storage}")
    private String mediaStorageTopic;
    private final KafkaTemplate<Long, Event<? extends EventPayload>> kafkaTemplate;
    private final EventRepository eventRepository;

    @Override
    public void send(Event<? extends EventPayload> event) {
        kafkaTemplate.send(mediaStorageTopic, EventUtil.uuidStringToLong(event.getEntityId()), event);
        log.info("kafka send 'media-storage' event with entity-id {}", event.getEntityId());
        eventRepository.save(event);
        kafkaTemplate.flush();
    }
}
