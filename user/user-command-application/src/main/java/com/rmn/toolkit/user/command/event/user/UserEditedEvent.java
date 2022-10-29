package com.rmn.toolkit.user.command.event.user;

import com.rmn.toolkit.user.command.event.Event;
import com.rmn.toolkit.user.command.event.EventPayload;
import lombok.*;
import lombok.experimental.SuperBuilder;
import javax.persistence.DiscriminatorValue;

import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "USER_EDITED")
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
        private String email;
        private String password;
        private String securityQuestion;
        private String securityAnswer;
        private String pinCode;
    }
}
