package com.rmn.toolkit.user.query.message.projector;

import com.rmn.toolkit.user.query.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.user.query.event.user.NotificationChangeStateEvent;
import com.rmn.toolkit.user.query.event.user.UserDeletedEvent;
import com.rmn.toolkit.user.query.event.user.UserEditedEvent;
import com.rmn.toolkit.user.query.model.Notification;
import com.rmn.toolkit.user.query.model.User;
import com.rmn.toolkit.user.query.model.type.NotificationType;
import com.rmn.toolkit.user.query.util.ProjectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserProjector {
    private static final List<Notification> NOTIFICATIONS =
            Arrays.asList(new Notification(NotificationType.SMS , false),
                    new Notification(NotificationType.PUSH , false),
                    new Notification(NotificationType.EMAIL , false));
    private final ProjectionUtil projectionUtil;

    public User project(ClientRegisteredEvent event){
        var payload = event.getPayload();
        return User.builder()
                .id(event.getEntityId())
                .password(payload.getPassword())
                .notifications(NOTIFICATIONS)
                .securityAnswer(payload.getSecurityAnswer())
                .securityQuestion(payload.getSecurityQuestion())
                .version(1)
                .build();
    }

    public void project(UserEditedEvent event, User user) {
        projectionUtil.validateEvent(event, user.getId(), user.getVersion());
        var payload = event.getPayload();
        user.setPassword(payload.getPassword());
        user.setSecurityAnswer(payload.getSecurityAnswer());
        user.setNotifications(user.getNotifications());
        user.setEmail(payload.getEmail());
        user.setSecurityQuestion(payload.getSecurityQuestion());
        user.setPinCode(payload.getPinCode());
        user.incrementVersion();
    }

    public void project(NotificationChangeStateEvent event, User user) {
        var payload = event.getPayload();
        user.setNotifications(payload.getNotifications());
    }

    public void project(UserDeletedEvent event, User user) {
        projectionUtil.validateEvent(event, user.getId(), user.getVersion());
    }

}
