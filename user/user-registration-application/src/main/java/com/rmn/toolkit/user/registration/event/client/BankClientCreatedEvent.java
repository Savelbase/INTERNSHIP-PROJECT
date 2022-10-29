package com.rmn.toolkit.user.registration.event.client;

import com.rmn.toolkit.user.registration.event.Event;
import com.rmn.toolkit.user.registration.event.EventPayload;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class BankClientCreatedEvent extends Event<BankClientCreatedEvent.Payload> {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    @Builder
    public static class Payload extends EventPayload {
        private String id;
        private String mobilePhone;
        private String passportNumber;
        private boolean resident;
        private String firstName;
        private String lastName;
        private String middleName;
        private String securityQuestion;
        private String securityAnswer;
        private boolean bankClient;
    }
}
