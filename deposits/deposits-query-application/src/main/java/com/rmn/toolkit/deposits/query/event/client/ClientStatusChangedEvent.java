package com.rmn.toolkit.deposits.query.event.client;


import com.rmn.toolkit.deposits.query.event.Event;
import com.rmn.toolkit.deposits.query.event.EventPayload;
import com.rmn.toolkit.deposits.query.model.type.ClientStatusType;
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
        private ClientStatusType status;
    }
}
