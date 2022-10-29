package com.rmn.toolkit.authorization.util;

import com.rmn.toolkit.authorization.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.authorization.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPayloadUtil {

    public ClientStatusChangedEvent.Payload createUserBlockedEventPayload(User user){
        return ClientStatusChangedEvent.Payload.builder()
                .attemptCounter(user.getAttemptCounter())
                .status(user.getStatus())
                .build();
    }
}
