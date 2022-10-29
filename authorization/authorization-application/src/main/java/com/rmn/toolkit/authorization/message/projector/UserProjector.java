package com.rmn.toolkit.authorization.message.projector;

import com.rmn.toolkit.authorization.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.authorization.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.authorization.event.user.UserDeletedEvent;
import com.rmn.toolkit.authorization.event.user.UserEditedEvent;
import com.rmn.toolkit.authorization.model.User;
import com.rmn.toolkit.authorization.model.type.UserStatusType;
import com.rmn.toolkit.authorization.util.ProjectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserProjector {
    private static final int VERSION = 1;
    private static final int ATTEMPT_COUNTER = 0;

    private final ProjectionUtil projectionUtil;

    public User project(ClientRegisteredEvent event) {
        var payload = event.getPayload();
        return User.builder()
                .id(event.getEntityId())
                .mobilePhone(payload.getMobilePhone())
                .password(payload.getPassword())
                .roleId(payload.getRoleId())
                .status(UserStatusType.ACTIVE)
                .attemptCounter(ATTEMPT_COUNTER)
                .version(VERSION)
                .build();
    }

    public void project(UserEditedEvent event, User user) {
        projectionUtil.validateEvent(event, user.getId(), user.getVersion());

        var payload = event.getPayload();
        user.setPassword(payload.getPassword());
        user.setPinCode(payload.getPinCode());
        user.incrementVersion();
    }

    public void project(ClientStatusChangedEvent event, User user) {
        var payload = event.getPayload();

        Integer attemptCounter = payload.getAttemptCounter();
        if (attemptCounter != null) {
            user.setAttemptCounter(attemptCounter);
        }

        user.setStatus(payload.getStatus());
    }

    public void project(UserDeletedEvent event, User user) {
        projectionUtil.validateEvent(event, user.getId(), user.getVersion());
    }
}
