package com.rmn.toolkit.user.registration.message.projector;

import com.rmn.toolkit.user.registration.event.client.BankClientCreatedEvent;
import com.rmn.toolkit.user.registration.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.user.registration.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientProjector {
    private static final int VERSION = 1;

    public Client project(ClientRegisteredEvent event) {
        var payload = event.getPayload();

        return Client.builder()
                .id(event.getEntityId())
                .mobilePhone(payload.getMobilePhone())
                .firstName(payload.getFirstName())
                .lastName(payload.getLastName())
                .middleName(payload.getMiddleName())
                .passportNumber(payload.getPassportNumber())
                .resident(payload.isResident())
                .password(payload.getPassword())
                .securityQuestion(payload.getSecurityQuestion())
                .securityAnswer(payload.getSecurityAnswer())
                .roleId(payload.getRoleId())
                .verificationCodeId(payload.getVerificationCodeId())
                .bankClient(payload.isBankClient())
                .registered(payload.isRegistered())
                .accessionDateTime(payload.getAccessionDateTime())
                .version(VERSION)
                .build();
    }

    public Client project(BankClientCreatedEvent event, String roleId) {
        var payload = event.getPayload();

        return Client.builder()
                .id(event.getEntityId())
                .mobilePhone(payload.getMobilePhone())
                .passportNumber(payload.getPassportNumber())
                .resident(payload.isResident())
                .firstName(payload.getFirstName())
                .lastName(payload.getLastName())
                .middleName(payload.getMiddleName())
                .securityQuestion(payload.getSecurityQuestion())
                .securityAnswer(payload.getSecurityAnswer())
                .bankClient(payload.isBankClient())
                .roleId(roleId)
                .version(VERSION)
                .build();
    }
}
