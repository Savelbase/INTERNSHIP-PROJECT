package com.rmn.toolkit.authorization.util;

import com.rmn.toolkit.authorization.event.Event;
import com.rmn.toolkit.authorization.event.EventPayload;
import com.rmn.toolkit.authorization.exception.ProjectionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProjectionUtil {

    public void validateEvent(Event<? extends EventPayload> event, String entityId, Integer entityCurrentVersion) {
        String userIdFromEvent = event.getEntityId();
        if (!userIdFromEvent.equals(entityId)) {
            log.error("Entity with id={} doesn't match to event with entityId={}", entityId, userIdFromEvent);
            throw new ProjectionException(String.format("Entity with id=%s doesn't match to event with entityId=%s",
                    entityId, userIdFromEvent));
        }

        Integer entityVersionFromEvent = event.getVersion();
        if (entityVersionFromEvent != (entityCurrentVersion + 1)) {
            log.error("Event version={} doesn't match to entity with version={}", entityVersionFromEvent,
                    entityCurrentVersion);
            throw new ProjectionException(String.format("Event version=%s doesn't match to entity with version=%s",
                    entityVersionFromEvent, entityCurrentVersion));
        }
    }
}
