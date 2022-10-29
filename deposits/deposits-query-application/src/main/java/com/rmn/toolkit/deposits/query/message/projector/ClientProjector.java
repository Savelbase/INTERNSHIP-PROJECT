package com.rmn.toolkit.deposits.query.message.projector;



import com.rmn.toolkit.deposits.query.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.deposits.query.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.deposits.query.model.Client;
import com.rmn.toolkit.deposits.query.model.type.ClientStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientProjector {

    public Client project(ClientRegisteredEvent event) {
        var payload = event.getPayload();
        return Client.builder()
                .id(event.getEntityId())
                .status(ClientStatusType.ACTIVE)
                .firstName(payload.getFirstName())
                .lastName(payload.getLastName())
                .build();
    }

    public void project(ClientStatusChangedEvent event, Client client) {
        var payload =  event.getPayload();
        client.setStatus(payload.getStatus());
    }
}
