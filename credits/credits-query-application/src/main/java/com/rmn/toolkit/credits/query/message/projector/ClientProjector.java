package com.rmn.toolkit.credits.query.message.projector;

import com.rmn.toolkit.credits.query.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.credits.query.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.credits.query.model.Client;
import com.rmn.toolkit.credits.query.model.type.ClientStatusType;
import org.springframework.stereotype.Component;

@Component
public class ClientProjector {
    private static final int VERSION = 1;

    public Client project(ClientRegisteredEvent event) {
        return Client.builder()
                .id(event.getEntityId())
                .status(ClientStatusType.ACTIVE)
                .version(VERSION)
                .build();
    }

    public void project(ClientStatusChangedEvent event, Client client) {
        var payload =  event.getPayload();
        client.setStatus(payload.getStatus());
    }
}
