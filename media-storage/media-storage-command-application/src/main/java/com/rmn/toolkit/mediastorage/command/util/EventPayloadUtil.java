package com.rmn.toolkit.mediastorage.command.util;

import com.rmn.toolkit.mediastorage.command.event.avatar.AvatarUploadedEvent;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class EventPayloadUtil {

    public AvatarUploadedEvent.Payload createAvatarCreatedEventPayload(String filename, String extension) {
        return AvatarUploadedEvent.Payload.builder()
                .url(String.format("%s.%s", filename, extension))
                .confirmed(true)
                .dateTime(ZonedDateTime.now())
                .build();
    }
}
