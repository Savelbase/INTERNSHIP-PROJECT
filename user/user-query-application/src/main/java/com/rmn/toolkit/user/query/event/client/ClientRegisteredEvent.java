package com.rmn.toolkit.user.query.event.client;

import com.rmn.toolkit.user.query.event.Event;
import com.rmn.toolkit.user.query.event.EventPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ClientRegisteredEvent extends Event<ClientRegisteredEvent.Payload> {

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class Payload extends EventPayload {
        private String firstName;
        private String lastName;
        private String middleName;
        private String mobilePhone;
        private String passportNumber;
        private String password;
        private String securityQuestion;
        private String securityAnswer;
        private boolean resident;
        private boolean registered;
        private ZonedDateTime accessionDateTime;
        private String roleId ;
    }
}
