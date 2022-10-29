package com.rmn.toolkit.user.command.service;

import com.rmn.toolkit.user.command.dto.request.CodeDto;
import com.rmn.toolkit.user.command.dto.request.NotificationDto;
import com.rmn.toolkit.user.command.event.EventType;
import com.rmn.toolkit.user.command.event.user.NotificationChangeStateEvent;
import com.rmn.toolkit.user.command.event.user.UserEditedEvent;
import com.rmn.toolkit.user.command.message.EventSender;
import com.rmn.toolkit.user.command.model.User;
import com.rmn.toolkit.user.command.security.SecurityUtil;
import com.rmn.toolkit.user.command.util.ClientUtil;
import com.rmn.toolkit.user.command.util.EventPayloadUtil;
import com.rmn.toolkit.user.command.util.EventUtil;
import com.rmn.toolkit.user.command.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDataService {
    private final EventSender eventSender ;
    private final EventUtil eventUtil;
    private final UserUtil userUtil;
    private final EventPayloadUtil eventPayloadUtil;
    private final SecurityUtil securityUtil;
    private final ClientUtil clientUtil;

    @Transactional
    public void createPinCode(CodeDto codeDto, String userId) {
        User user = userUtil.findUserById(userId);
        String pinCodeHash = userUtil.encodeValue(codeDto.getCode());
        user.setPinCode(pinCodeHash);

        UserEditedEvent.Payload payload = eventPayloadUtil.createUserEditEventPayload(user);
        UserEditedEvent event = UserEditedEvent.builder().build();
        eventUtil.populateEventFields(event, EventType.USER_EDITED, user.getId() , user.getVersion() + 1 , user.getId(), payload);
        eventSender.send(event);
    }

    @Transactional
    public void changeNotification(NotificationDto notificationDto) {
        int COUNT = -1 ;
        String userId = securityUtil.getCurrentUserId();
        clientUtil.checkIfClientIsBlocked(userId);
        User user = userUtil.findUserById(userId);
        do{
            COUNT++;
            if(user.getNotifications().get(COUNT).getType().equals(notificationDto.getType())){
                user.getNotifications().get(COUNT).setState(notificationDto.isState());
            }
        }while(!Objects.equals(user.getNotifications().get(COUNT).getType() , notificationDto.getType()));
        NotificationChangeStateEvent.Payload payload = eventPayloadUtil.createNotificationChangeStateEventPayload(user);
        NotificationChangeStateEvent event = NotificationChangeStateEvent.builder().build();
        eventUtil.populateEventFields(event, EventType.NOTIFICATION_CHANGE_STATE, user.getId() , user.getVersion(), user.getId(), payload);
        eventSender.send(event);
    }

    @Transactional
    public void changeEmail(String email) {
        String userId = securityUtil.getCurrentUserId();
        clientUtil.checkIfClientIsBlocked(userId);
        User user = userUtil.findUserById(userId);
        user.setEmail(email);
        UserEditedEvent.Payload payload = eventPayloadUtil.createUserEditEventPayload(user);
        UserEditedEvent event = UserEditedEvent.builder().build();
        eventUtil.populateEventFields(event, EventType.USER_EDITED, user.getId() , user.getVersion() + 1 , user.getId(), payload);
        eventSender.send(event);
    }
}
