package com.rmn.toolkit.mediastorage.command.message.projector;

import com.rmn.toolkit.mediastorage.command.event.avatar.AvatarUploadedEvent;
import com.rmn.toolkit.mediastorage.command.model.MediaFileData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MediaFileDataProjector {
    private static final int VERSION = 1;

    public MediaFileData project(AvatarUploadedEvent event) {
        var payload = event.getPayload();

        return MediaFileData.builder()
                .id(event.getEntityId())
                .url(payload.getUrl())
                .confirmed(payload.isConfirmed())
                .uploadedDateTime(payload.getDateTime())
                .userId(event.getAuthorId())
                .version(VERSION)
                .build();
    }
}