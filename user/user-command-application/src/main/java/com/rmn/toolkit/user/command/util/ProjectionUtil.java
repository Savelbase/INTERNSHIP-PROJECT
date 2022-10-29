package com.rmn.toolkit.user.command.util;

import com.rmn.toolkit.user.command.event.Event;
import com.rmn.toolkit.user.command.event.EventPayload;
import com.rmn.toolkit.user.command.exception.ProjectionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProjectionUtil {

    public void validateEvent(Event<? extends EventPayload> event, String entityId, Integer entityCurrentVersion) {
        String userIdFromEvent = event.getEntityId();
        if (!userIdFromEvent.equals(entityId)) {
            log.error("Entity id {} and event entityId {} do not match", entityId, userIdFromEvent);
            throw new ProjectionException(String.format("Entity id %s and event entityId %s do not match",
                    entityId, userIdFromEvent));
        }

        Integer entityVersionFromEvent = event.getVersion();
        if (entityVersionFromEvent != (entityCurrentVersion + 1)) {
            log.error("Event version {} does not match entity with version {}\n", entityVersionFromEvent,
                    entityCurrentVersion);
            throw new ProjectionException(String.format("Event version %s does not match entity with version %s",
                    entityVersionFromEvent, entityCurrentVersion));
        }
    }

}
