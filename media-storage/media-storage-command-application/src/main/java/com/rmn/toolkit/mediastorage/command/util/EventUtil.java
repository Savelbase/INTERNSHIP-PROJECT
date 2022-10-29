package com.rmn.toolkit.mediastorage.command.util;

import com.rmn.toolkit.mediastorage.command.event.Event;
import com.rmn.toolkit.mediastorage.command.event.EventPayload;
import com.rmn.toolkit.mediastorage.command.event.EventType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class EventUtil {
    @Value(value = "${spring.application.name}")
    private String context;

    public <T extends EventPayload, S extends Event<T>> void populateEventFields(S event, EventType type,
                                                                                 String entityId, Integer version,
                                                                                 String authorId, T payload) {
        populateEventFields(event, type, entityId, version, authorId, payload, null);
    }

    public <T extends EventPayload, S extends Event<T>> void populateEventFields(S event, EventType type,
                                                                                 String entityId, Integer version,
                                                                                 String authorId, T payload,
                                                                                 String parentId) {
        event.setId(UUID.randomUUID().toString());
        event.setType(type);
        event.setContext(context);
        event.setEntityId(entityId);
        event.setVersion(version);
        event.setAuthorId(authorId);
        event.setPayload(payload);
        event.setParentId(parentId);
        event.setDateTime(Instant.now());
    }

    public static Long uuidStringToLong(String uuid) {
        return UUID.fromString(uuid).getMostSignificantBits() & Long.MAX_VALUE;
    }
}
