package com.rmn.toolkit.mediastorage.query.event.client;

import com.rmn.toolkit.mediastorage.query.event.Event;
import com.rmn.toolkit.mediastorage.query.event.EventPayload;
import com.rmn.toolkit.mediastorage.query.model.type.UserStatusType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ClientStatusChangedEvent extends Event<ClientStatusChangedEvent.Payload> {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = true)
    public static class Payload extends EventPayload {
        private UserStatusType status;
    }
}
