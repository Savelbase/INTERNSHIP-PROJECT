package com.rmn.toolkit.user.command.service;

import com.rmn.toolkit.user.command.dto.request.ChangePasswordDto;
import com.rmn.toolkit.user.command.dto.request.SecurityQADto;
import com.rmn.toolkit.user.command.event.EventType;
import com.rmn.toolkit.user.command.event.user.UserEditedEvent;
import com.rmn.toolkit.user.command.message.EventSender;
import com.rmn.toolkit.user.command.model.User;
import com.rmn.toolkit.user.command.util.EventPayloadUtil;
import com.rmn.toolkit.user.command.util.EventUtil;
import com.rmn.toolkit.user.command.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserSecurityService {
    private final UserUtil userUtil;
    private final EventPayloadUtil eventPayloadUtil;
    private final EventUtil eventUtil;
    private final EventSender eventSender;

    @Transactional
    public void changeQA(SecurityQADto qaDto, String clientId) {
        User user = userUtil.findUserById(clientId);
        user.setSecurityAnswer(qaDto.getAnswer());
        user.setSecurityQuestion(qaDto.getQuestion());
        UserEditedEvent.Payload payload = eventPayloadUtil.createUserEditEventPayload(user);
        UserEditedEvent event = UserEditedEvent.builder().build();
        eventUtil.populateEventFields(event, EventType.USER_EDITED, user.getId() , user.getVersion()+1 , user.getId(), payload);
        eventSender.send(event);
    }

    @Transactional
    public void changePassword(ChangePasswordDto passwordDto, String clientId) {
        User user = userUtil.findUserById(clientId);
        userUtil.checkIfOldPasswordMatch(passwordDto , user);
        String passwordHash = userUtil.encodeValue(passwordDto.getNewPassword());
        user.setPassword(passwordHash);
        UserEditedEvent.Payload payload = eventPayloadUtil.createUserEditEventPayload(user);
        UserEditedEvent event = UserEditedEvent.builder().build();
        eventUtil.populateEventFields(event, EventType.USER_EDITED, user.getId() , user.getVersion()+1 , user.getId(), payload);
        eventSender.send(event);
    }
}
