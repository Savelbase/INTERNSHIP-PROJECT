package com.rmn.toolkit.user.command.event.client;

import com.rmn.toolkit.user.command.event.Event;
import com.rmn.toolkit.user.command.event.EventPayload;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

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
        private String middleName;
        private String mobilePhone;
        private String passportNumber;
        private String password;
        private String securityQuestion;
        private ZonedDateTime accessionDateTime;
        private String securityAnswer;
        private String roleId;
        private boolean resident;
        private boolean registered;
    }
}
