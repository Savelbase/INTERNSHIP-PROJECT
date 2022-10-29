package com.rmn.toolkit.user.registration.event.client;

import com.rmn.toolkit.user.registration.event.Event;
import com.rmn.toolkit.user.registration.event.EventPayload;
import com.rmn.toolkit.user.registration.event.EventTypeConstants;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.ZonedDateTime;

@Entity
@DiscriminatorValue(value = EventTypeConstants.CLIENT_REGISTERED)
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
        private String firstName;
        private String lastName;
        private String middleName;
        private String mobilePhone;
        private String passportNumber;
        private boolean resident;
        private String password;
        private String securityQuestion;
        private String securityAnswer;
        private String roleId;
        private String verificationCodeId;
        private ZonedDateTime accessionDateTime;
        private boolean bankClient;
        private boolean registered;
    }
}
