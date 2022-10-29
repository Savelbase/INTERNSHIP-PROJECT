package com.rmn.toolkit.user.registration.util;

import com.rmn.toolkit.user.registration.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.user.registration.model.Client;
import com.rmn.toolkit.user.registration.model.Role;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class EventPayloadUtil {

    public ClientRegisteredEvent.Payload createClientRegisteredEventPayload(Client client, Role role) {
        return ClientRegisteredEvent.Payload.builder()
                .mobilePhone(client.getMobilePhone())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .middleName(client.getMiddleName())
                .passportNumber(client.getPassportNumber())
                .resident(client.isResident())
                .password(client.getPassword())
                .securityQuestion(client.getSecurityQuestion())
                .securityAnswer(client.getSecurityAnswer())
                .roleId(role.getId())
                .verificationCodeId(client.getVerificationCodeId())
                .bankClient(true)
                .registered(true)
                .accessionDateTime(ZonedDateTime.now())
                .build();
    }
}
