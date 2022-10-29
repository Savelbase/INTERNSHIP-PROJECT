package com.rmn.toolkit.bankclient.util;

import com.rmn.toolkit.bankclient.event.client.BankClientCreatedEvent;
import com.rmn.toolkit.bankclient.model.Client;
import org.springframework.stereotype.Component;

@Component
public class EventPayloadUtil {

    public BankClientCreatedEvent.Payload createBankClientCreatedEventPayload(Client client) {
        return BankClientCreatedEvent.Payload.builder()
                .id(client.getId())
                .mobilePhone(client.getMobilePhone())
                .passportNumber(client.getPassportNumber())
                .resident(client.isResident())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .middleName(client.getMiddleName())
                .securityQuestion(client.getSecurityQuestion())
                .securityAnswer(client.getSecurityAnswer())
                .bankClient(client.isBankClient())
                .build();
    }
}
