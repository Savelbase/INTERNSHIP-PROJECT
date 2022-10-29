package com.rmn.toolkit.authorization.event.client;

import com.rmn.toolkit.authorization.event.Event;
import com.rmn.toolkit.authorization.event.EventPayload;
import com.rmn.toolkit.authorization.event.EventTypeConstants;
import com.rmn.toolkit.authorization.model.type.UserStatusType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(EventTypeConstants.CLIENT_STATUS_CHANGED)
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
        private Integer attemptCounter;
        private UserStatusType status;
    }
}
