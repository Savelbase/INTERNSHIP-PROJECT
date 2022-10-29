package com.rmn.toolkit.credits.command.message.projector;

import com.rmn.toolkit.credits.command.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.credits.command.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.credits.command.model.Client;
import com.rmn.toolkit.credits.command.model.type.ClientStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
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
