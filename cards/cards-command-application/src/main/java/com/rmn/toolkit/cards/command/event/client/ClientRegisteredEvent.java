package com.rmn.toolkit.cards.command.event.client;

import com.rmn.toolkit.cards.command.event.Event;
import com.rmn.toolkit.cards.command.event.EventPayload;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ClientRegisteredEvent extends Event<ClientRegisteredEvent.Payload> {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = true)
    public static class Payload extends EventPayload {
        private String firstName;
        private String lastName;
    }
}
