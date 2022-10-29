package com.rmn.toolkit.user.command.service;

import com.rmn.toolkit.user.command.event.client.ApprovedBankClientEvent;
import com.rmn.toolkit.user.command.event.EventType;
import com.rmn.toolkit.user.command.event.user.UserDeletedEvent;
import com.rmn.toolkit.user.command.message.EventSender;
import com.rmn.toolkit.user.command.model.*;
import com.rmn.toolkit.user.command.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCommandService {
    private final EventSender eventSender ;
    private final EventUtil eventUtil;
    private final UserUtil userUtil;
    private final EventPayloadUtil eventPayloadUtil;

    @Transactional
    public void approveBankClient(Client client, String authorId) {
        client.setBankClient(true);
        ApprovedBankClientEvent.Payload payload = eventPayloadUtil.createApprovedBankClientEvent(client.isBankClient());
        ApprovedBankClientEvent event = ApprovedBankClientEvent.builder().build();
        eventUtil.populateEventFields(event, EventType.APPROVED_BANK_CLIENT, client.getId() , client.getVersion(), authorId, payload);
        eventSender.send(event);
    }

    @Transactional
    public void deleteUserById(String userId, String authorId) {
        User user = userUtil.findUserById(userId);
        UserDeletedEvent.Payload payload = UserDeletedEvent.Payload.builder().build();
        UserDeletedEvent event = UserDeletedEvent.builder().build();
        eventUtil.populateEventFields(event, EventType.USER_DELETED, user.getId(), user.getVersion() + 1, authorId, payload);
        eventSender.send(event);
    }
}
