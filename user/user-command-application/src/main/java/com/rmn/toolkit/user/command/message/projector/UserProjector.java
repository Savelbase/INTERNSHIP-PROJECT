package com.rmn.toolkit.user.command.message.projector;

import com.rmn.toolkit.user.command.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.user.command.event.user.NotificationChangeStateEvent;
import com.rmn.toolkit.user.command.event.user.UserDeletedEvent;
import com.rmn.toolkit.user.command.event.user.UserEditedEvent;
import com.rmn.toolkit.user.command.model.Client;
import com.rmn.toolkit.user.command.model.DeletedUser;
import com.rmn.toolkit.user.command.model.Notification;
import com.rmn.toolkit.user.command.model.User;
import com.rmn.toolkit.user.command.model.type.NotificationType;
import com.rmn.toolkit.user.command.util.ProjectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserProjector {
    private static final List<Notification> NOTIFICATIONS =
            Arrays.asList(new Notification(NotificationType.SMS , false),
                    new Notification(NotificationType.PUSH , false),
                    new Notification(NotificationType.EMAIL , false));
    private static final int VERSION = 1;

    private final ProjectionUtil projectionUtil;

    public User project(ClientRegisteredEvent event){
        var payload = event.getPayload();
        return User.builder()
                .id(event.getEntityId())
                .password(payload.getPassword())
                .notifications(NOTIFICATIONS)
                .securityAnswer(payload.getSecurityAnswer())
                .securityQuestion(payload.getSecurityQuestion())
                .version(VERSION)
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

    public User project(NotificationChangeStateEvent event, User user) {
        var payload = event.getPayload();

        return User.builder()
                .id(event.getEntityId())
                .notifications(payload.getNotifications())
                .email(user.getEmail())
                .password(user.getPassword())
                .securityQuestion(user.getSecurityQuestion())
                .securityAnswer(user.getSecurityAnswer())
                .pinCode(user.getPinCode())
                .version(user.getVersion())
                .build();
    }

    public DeletedUser project(UserDeletedEvent event, User user, Client client) {
        projectionUtil.validateEvent(event, user.getId(), user.getVersion());

        return DeletedUser.builder()
                .id(event.getEntityId())
                .mobilePhone(client.getMobilePhone())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .middleName(client.getMiddleName())
                .accessionDateTime(client.getAccessionDateTime())
                .passportNumber(client.getPassportNumber())
                .resident(client.isResident())
                .roleId(client.getRoleId())
                .status(client.getStatus())
                .notifications(user.getNotifications())
                .email(user.getEmail())
                .password(user.getPassword())
                .pinCode(user.getPinCode())
                .securityQuestion(user.getSecurityQuestion())
                .securityAnswer(user.getSecurityAnswer())
                .build();
    }
}
