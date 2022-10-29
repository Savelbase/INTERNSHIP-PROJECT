package com.rmn.toolkit.user.command.event.client;

import com.rmn.toolkit.user.command.event.Event;
import com.rmn.toolkit.user.command.event.EventPayload;
import com.rmn.toolkit.user.command.model.type.ClientStatusType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "CLIENT_STATUS_CHANGED")
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
        private ClientStatusType status;
    }
}
