package com.rmn.toolkit.user.command.util;

import com.rmn.toolkit.user.command.event.client.ApprovedBankClientEvent;
import com.rmn.toolkit.user.command.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.user.command.event.user.NotificationChangeStateEvent;
import com.rmn.toolkit.user.command.event.user.UserEditedEvent;
import com.rmn.toolkit.user.command.model.Client;
import com.rmn.toolkit.user.command.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPayloadUtil {

    public UserEditedEvent.Payload createUserEditEventPayload(User user){
        return UserEditedEvent.Payload.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .securityQuestion(user.getSecurityQuestion())
                .securityAnswer(user.getSecurityAnswer())
                .pinCode(user.getPinCode())
                .build();
    }

    public NotificationChangeStateEvent.Payload createNotificationChangeStateEventPayload(User user){
        return NotificationChangeStateEvent.Payload.builder()
                .notifications(user.getNotifications())
                .build();
    }

    public ClientStatusChangedEvent.Payload createClientStatusChangedEventPayload(Client client){
        return ClientStatusChangedEvent.Payload.builder()
                .status(client.getStatus())
                .build();
    }

    public ApprovedBankClientEvent.Payload createApprovedBankClientEvent(boolean bankClient) {
        return ApprovedBankClientEvent.Payload.builder()
                .bankClient(bankClient)
                .build();
    }
}
