package com.rmn.toolkit.mediastorage.command.message.projector;

import com.rmn.toolkit.mediastorage.command.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.mediastorage.command.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.mediastorage.command.model.User;
import com.rmn.toolkit.mediastorage.command.model.type.UserStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientProjector {
    private static final int VERSION = 1;

    public User project(ClientRegisteredEvent event) {
        return User.builder()
                .id(event.getEntityId())
                .status(UserStatusType.ACTIVE)
                .version(VERSION)
                .build();
    }

    public void project(ClientStatusChangedEvent event, User user) {
        var payload =  event.getPayload();
        user.setStatus(payload.getStatus());
    }
}
