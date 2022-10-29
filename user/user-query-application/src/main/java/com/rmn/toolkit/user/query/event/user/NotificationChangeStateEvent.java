package com.rmn.toolkit.user.query.event.user;

import com.rmn.toolkit.user.query.event.Event;
import com.rmn.toolkit.user.query.event.EventPayload;
import com.rmn.toolkit.user.query.model.Notification;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class NotificationChangeStateEvent extends Event<NotificationChangeStateEvent.Payload> {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = true)
    public static class Payload extends EventPayload {
        private List<Notification> notifications;
    }
}
