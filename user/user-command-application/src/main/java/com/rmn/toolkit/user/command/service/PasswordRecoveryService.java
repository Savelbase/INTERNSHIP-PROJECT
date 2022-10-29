package com.rmn.toolkit.user.command.service;

import com.rmn.toolkit.user.command.dto.request.PasswordDto;
import com.rmn.toolkit.user.command.event.EventType;
import com.rmn.toolkit.user.command.event.user.UserEditedEvent;
import com.rmn.toolkit.user.command.exception.locked.MaxLimitExceededException;
import com.rmn.toolkit.user.command.exception.notfound.VerificationCodeNotFoundException;
import com.rmn.toolkit.user.command.exception.unauthorized.IncorrectVerificationCodeException;
import com.rmn.toolkit.user.command.message.EventSender;
import com.rmn.toolkit.user.command.model.Client;
import com.rmn.toolkit.user.command.model.Role;
import com.rmn.toolkit.user.command.model.User;
import com.rmn.toolkit.user.command.model.VerificationCode;
import com.rmn.toolkit.user.command.model.type.VerificationCodeType;
import com.rmn.toolkit.user.command.security.AuthorityType;
import com.rmn.toolkit.user.command.security.jwt.service.TokenService;
import com.rmn.toolkit.user.command.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordRecoveryService {
    private final VerificationUtil verificationUtil;
    private final EventUtil eventUtil;
    private final UserUtil userUtil;
    private final EventPayloadUtil eventPayloadUtil;
    private final RoleUtil roleUtil;
    private final TokenService tokenService;
    private final EventSender eventSender;

    @Transactional
    public String createVerificationCode(String clientId) {
       return verificationUtil.createVerificationCode(clientId , VerificationCodeType.RECOVERY_PASSWORD);
    }

    @Transactional(noRollbackFor = {
            IncorrectVerificationCodeException.class,
            MaxLimitExceededException.class
    })
    public void checkVerificationCode(String verificationCode , String clientId){
        VerificationCode verification = verificationUtil.findNotVerifiedCodeByClientId(clientId);
        if (verification.getAppointment().equals(VerificationCodeType.RECOVERY_PASSWORD)){
            verificationUtil.checkVerificationCode(verificationCode , clientId);
        }else {
            throw new VerificationCodeNotFoundException(clientId);
        }
    }

    @Transactional
    public void savePassword(PasswordDto passwordDto, String clientId) {
        User user = userUtil.findUserById(clientId);
        String passwordHash = userUtil.encodeValue(passwordDto.getPassword());
        user.setPassword(passwordHash);
        UserEditedEvent.Payload payload = eventPayloadUtil.createUserEditEventPayload(user);
        UserEditedEvent event = UserEditedEvent.builder().build();
        eventUtil.populateEventFields(event, EventType.USER_EDITED, user.getId() , user.getVersion()+1 , user.getId(), payload);
        eventSender.send(event);
    }

    @Transactional(readOnly = true)
    public String checkClientByMobilePhoneAndGenerateToken(Client client) {
        Role role = roleUtil.findRoleById(client.getRoleId());
        return tokenService.generateAccessToken(client.getId() , role.getAuthorities());
    }

    @Transactional(readOnly = true)
    public String createAccessTokenWithPasswordRecoveryAuthorityType(Client client) {
        Role role = roleUtil.findRoleById(client.getRoleId());
        List<AuthorityType> authorityList = new ArrayList<>(Arrays.asList(role.getAuthorities()));
        authorityList.add(AuthorityType.PASSWORD_RECOVERY);
        AuthorityType[] newAuthorities = authorityList.toArray(new AuthorityType[0]);
        return tokenService.generateAccessToken(client.getId() , newAuthorities);
    }
}
