package com.rmn.toolkit.user.command.service;

import com.rmn.toolkit.user.command.dto.request.CodeDto;
import com.rmn.toolkit.user.command.event.EventType;
import com.rmn.toolkit.user.command.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.user.command.event.user.UserEditedEvent;
import com.rmn.toolkit.user.command.exception.notfound.VerificationCodeNotFoundException;
import com.rmn.toolkit.user.command.exception.unauthorized.IncorrectVerificationCodeException;
import com.rmn.toolkit.user.command.exception.locked.MaxLimitExceededException;
import com.rmn.toolkit.user.command.message.EventSender;
import com.rmn.toolkit.user.command.model.*;
import com.rmn.toolkit.user.command.model.type.ClientStatusType;
import com.rmn.toolkit.user.command.model.type.VerificationCodeType;
import com.rmn.toolkit.user.command.security.AuthorityType;
import com.rmn.toolkit.user.command.security.jwt.service.TokenService;
import com.rmn.toolkit.user.command.util.*;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PinCodeResetService {
    private final VerificationUtil verificationUtil;
    private final EventUtil eventUtil;
    private final UserUtil userUtil;
    private final ClientUtil clientUtil;
    private final EventPayloadUtil eventPayloadUtil;
    private final RoleUtil roleUtil;
    private final TokenService tokenService;
    private final EventSender eventSender;

    @Transactional
    public String createVerificationCode(String clientId) {
       return verificationUtil.createVerificationCode(clientId , VerificationCodeType.RESET_PIN_CODE);
    }

    @Transactional(noRollbackFor = {
            IncorrectVerificationCodeException.class,
            MaxLimitExceededException.class
    })
    public void checkVerificationCode(String verificationCode , String clientId){
        VerificationCode verification = verificationUtil.findNotVerifiedCodeByClientId(clientId);
        if (verification.getAppointment().equals(VerificationCodeType.RESET_PIN_CODE)){
            verificationUtil.checkVerificationCode(verificationCode , clientId);
        }else {
            throw new VerificationCodeNotFoundException(clientId);
        }
    }

    @Transactional
    public void savePinCode(CodeDto codeDto, String clientId) {
        User user = userUtil.findUserById(clientId);
        String codeHash = userUtil.encodeValue(codeDto.getCode());
        user.setPinCode(codeHash);

        UserEditedEvent.Payload payload = eventPayloadUtil.createUserEditEventPayload(user);
        UserEditedEvent event = UserEditedEvent.builder().build();
        eventUtil.populateEventFields(event, EventType.USER_EDITED, user.getId() , user.getVersion()+1 , user.getId(), payload);
        eventSender.send(event);
    }

    @Transactional
    public void setStatusToActive(String clientId) {
        Client client = clientUtil.findClientById(clientId);
        client.setStatus(ClientStatusType.ACTIVE);

        ClientStatusChangedEvent.Payload clientPayload = eventPayloadUtil.createClientStatusChangedEventPayload(client);
        ClientStatusChangedEvent clientEvent = ClientStatusChangedEvent.builder().build();
        eventUtil.populateEventFields(clientEvent, EventType.CLIENT_STATUS_CHANGED, client.getId(),
                client.getVersion(), client.getId(), clientPayload);
        eventSender.send(clientEvent);
    }

    @Transactional(readOnly = true)
    public String checkClientByMobilePhoneAndGenerateToken(Client client) {
        Role role = roleUtil.findRoleById(client.getRoleId());
        return tokenService.generateAccessToken(client.getId() , role.getAuthorities());
    }

    @Transactional(readOnly = true)
    public String createAccessTokenWithPinResetAuthorityType(Client client) {
        Role role = roleUtil.findRoleById(client.getRoleId());
        List<AuthorityType> authorityList = new ArrayList<>(Arrays.asList(role.getAuthorities()));
        authorityList.add(AuthorityType.PIN_CODE_RESET);
        AuthorityType[] newAuthorities = authorityList.toArray(new AuthorityType[0]);
        return tokenService.generateAccessToken(client.getId() , newAuthorities);
    }
}
