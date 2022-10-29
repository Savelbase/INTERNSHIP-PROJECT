package com.rmn.toolkit.authorization.event.user;

import com.rmn.toolkit.authorization.event.Event;
import com.rmn.toolkit.authorization.event.EventPayload;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UserEditedEvent extends Event<UserEditedEvent.Payload> {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = true)
    public static class Payload extends EventPayload {
        private String password;
        private String pinCode;
    }
}
