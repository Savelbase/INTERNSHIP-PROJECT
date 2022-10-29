package com.rmn.toolkit.user.command.message.projector;

import com.rmn.toolkit.user.command.event.client.ApprovedBankClientEvent;
import com.rmn.toolkit.user.command.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.user.command.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.user.command.model.Client;
import com.rmn.toolkit.user.command.model.type.ClientStatusType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientProjector {

    public Client project(ClientRegisteredEvent event) {
        var payload = event.getPayload();

        return Client.builder()
                .id(event.getEntityId())
                .roleId(payload.getRoleId())
                .mobilePhone(payload.getMobilePhone())
                .firstName(payload.getFirstName())
                .lastName(payload.getLastName())
                .middleName(payload.getMiddleName())
                .passportNumber(payload.getPassportNumber())
                .resident(payload.isResident())
                .accessionDateTime(payload.getAccessionDateTime())
                .status(ClientStatusType.ACTIVE)
                .version(1)
                .build();
    }

    public void project(ClientStatusChangedEvent event, Client client) {
        var payload =  event.getPayload();
        client.setStatus(payload.getStatus());
    }

    public void project(ApprovedBankClientEvent event, Client client) {
        var payload =  event.getPayload();
        client.setBankClient(payload.isBankClient());
    }
}
