package com.rmn.toolkit.authorization.event.client;

import com.rmn.toolkit.authorization.event.Event;
import com.rmn.toolkit.authorization.event.EventPayload;
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
    @EqualsAndHashCode(callSuper = true)
    @Builder
    public static class Payload extends EventPayload {
        private String mobilePhone;
        private String password;
        private String roleId;
    }
}
