package com.rmn.toolkit.user.command.message.sender;

import com.rmn.toolkit.user.command.event.Event;
import com.rmn.toolkit.user.command.event.EventPayload;
import com.rmn.toolkit.user.command.message.EventSender;
import com.rmn.toolkit.user.command.repository.EventRepository;
import com.rmn.toolkit.user.command.util.EventUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventSenderKafkaImpl implements EventSender {
    @Value("${kafka.topic.user-edit}")
    private String userEditTopic;
    @Value("${kafka.topic.user-delete}")
    private String userDeleteTopic;
    @Value("${kafka.topic.client-status}")
    private String clientStatusTopic;
    @Value("${kafka.topic.approved-bank-client}")
    private String approvedBankClientTopic;
    @Value("${kafka.topic.notification}")
    private String notificationTopic;
    private final EventRepository eventRepository;
    private final KafkaTemplate<Long, Event<? extends EventPayload>> kafkaTemplate;

    @Override
    public void send(Event<? extends EventPayload> event) {
        switch (event.getType()) {
            case USER_EDITED -> {
                kafkaTemplate.send(userEditTopic, EventUtil.uuidStringToLong(event.getEntityId()), event);
                log.info("kafka send 'user-edit' event with entity-id {}", event.getEntityId());
            }
            case NOTIFICATION_CHANGE_STATE -> {
                kafkaTemplate.send(notificationTopic, EventUtil.uuidStringToLong(event.getEntityId()), event);
                log.info("kafka send 'change-notification-state' event with entity-id {}", event.getEntityId());
            }
            case CLIENT_STATUS_CHANGED -> {
                kafkaTemplate.send(clientStatusTopic, EventUtil.uuidStringToLong(event.getEntityId()), event);
                log.info("kafka send 'client-status-changed' event with entity-id {}", event.getEntityId());
            }
            case APPROVED_BANK_CLIENT -> {
                kafkaTemplate.send(approvedBankClientTopic, EventUtil.uuidStringToLong(event.getEntityId()), event);
                log.info("kafka send 'approved-bank-client' event with entity-id {}", event.getEntityId());
            }
            case USER_DELETED -> {
                kafkaTemplate.send(userDeleteTopic, EventUtil.uuidStringToLong(event.getEntityId()), event);
                log.info("kafka send 'user-deleted' event with entity-id {}", event.getEntityId());
            }
        }
        eventRepository.save(event);
        kafkaTemplate.flush();
    }
}
