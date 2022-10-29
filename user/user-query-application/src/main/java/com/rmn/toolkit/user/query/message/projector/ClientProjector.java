package com.rmn.toolkit.user.query.message.projector;

import com.rmn.toolkit.user.query.event.client.ApprovedBankClientEvent;
import com.rmn.toolkit.user.query.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.user.query.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.user.query.model.Client;
import com.rmn.toolkit.user.query.model.type.ClientStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientProjector {

    public Client project(ClientRegisteredEvent event) {
        var payload =  event.getPayload();
        return Client.builder()
                .id(event.getEntityId())
                .firstName(payload.getFirstName())
                .lastName(payload.getLastName())
                .middleName(payload.getMiddleName())
                .mobilePhone(payload.getMobilePhone())
                .passportNumber(payload.getPassportNumber())
                .resident(payload.isResident())
                .accessionDateTime(payload.getAccessionDateTime())
                .roleId(payload.getRoleId())
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
