package com.rmn.toolkit.mediastorage.command.event.avatar;

import com.rmn.toolkit.mediastorage.command.event.Event;
import com.rmn.toolkit.mediastorage.command.event.EventPayload;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.ZonedDateTime;

@Entity
@DiscriminatorValue("AVATAR_UPLOADED")
@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AvatarUploadedEvent extends Event<AvatarUploadedEvent.Payload> {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = true)
    public static class Payload extends EventPayload {
        protected String url;
        protected boolean confirmed;
        protected ZonedDateTime dateTime;
    }
}