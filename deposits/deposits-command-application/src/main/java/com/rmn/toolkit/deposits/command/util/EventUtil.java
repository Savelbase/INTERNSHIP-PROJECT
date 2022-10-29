package com.rmn.toolkit.deposits.command.util;


import com.rmn.toolkit.deposits.command.event.Event;
import com.rmn.toolkit.deposits.command.event.EventPayload;
import com.rmn.toolkit.deposits.command.event.EventType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class EventUtil {
    @Value("${spring.application.name}")
    private String context;

    /**
     * Заполнить все поля не каскадного Event. Event ID случайный.
     *
     * @param event    событие
     * @param entityId ID объекта, с которым связано событие
     * @param version  новая версия события, обычно должно быть на 1 больше текущего
     * @param authorId ID юзера, создавшего событие
     * @param payload  payload события
     */
    public <T extends EventPayload, S extends Event<T>> void populateEventFields(S event, EventType type,
                                                                                 String entityId, Integer version,
                                                                                 String authorId, T payload) {
        populateEventFields(event, type, entityId, version, authorId, payload, null);
    }

    /**
     * Заполнить все поля каскадного Event. Заполнится ли Event ID зависит от randomEventId.
     *
     * @param event    событие
     * @param entityId ID объекта, с которым связано событие
     * @param version  новая версия события, обычно должно быть на 1 больше текущего
     * @param authorId ID юзера, создавшего событие
     * @param payload  payload события
     * @param parentId Event ID, которое стало первопричиной этого события
     */
    public <T extends EventPayload, S extends Event<T>> void populateEventFields(S event, EventType type,
                                                                                 String entityId, Integer version,
                                                                                 String authorId, T payload,
                                                                                 String parentId) {
        event.setId(UUID.randomUUID().toString());
        event.setType(type);
        event.setEntityId(entityId);
        event.setAuthorId(authorId);
        event.setContext(context);
        event.setDateTime(Instant.now());
        event.setVersion(version);
        event.setParentId(parentId);
        event.setPayload(payload);
    }

    public static Long uuidStringToLong(String uuid) {
        return UUID.fromString(uuid).getMostSignificantBits() & Long.MAX_VALUE;
    }

}
