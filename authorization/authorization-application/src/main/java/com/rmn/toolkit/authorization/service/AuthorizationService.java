package com.rmn.toolkit.authorization.service;

import com.rmn.toolkit.authorization.dto.request.PhoneAndPasswordDto;
import com.rmn.toolkit.authorization.dto.request.PhoneAndPinCodeDto;
import com.rmn.toolkit.authorization.dto.response.success.TokensAndUserIdDto;
import com.rmn.toolkit.authorization.event.EventType;
import com.rmn.toolkit.authorization.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.authorization.exception.unauthorized.IncorrectPasswordException;
import com.rmn.toolkit.authorization.exception.unauthorized.IncorrectPinCodeException;
import com.rmn.toolkit.authorization.exception.locked.ApplicationIsLockedException;
import com.rmn.toolkit.authorization.message.EventSender;
import com.rmn.toolkit.authorization.model.User;
import com.rmn.toolkit.authorization.model.type.UserStatusType;
import com.rmn.toolkit.authorization.repository.EventRepository;
import com.rmn.toolkit.authorization.repository.RefreshTokenRepository;
import com.rmn.toolkit.authorization.util.EventPayloadUtil;
import com.rmn.toolkit.authorization.util.EventUtil;
import com.rmn.toolkit.authorization.util.ResponseUtil;
import com.rmn.toolkit.authorization.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizationService {
    private static final int MAX_LIMIT = 3;

    private final UserUtil userUtil;
    private final TokenService tokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final EventPayloadUtil eventPayloadUtil;
    private final EventUtil eventUtil;
    private final EventSender eventSender;
    private final EventRepository eventRepository;
    private final ResponseUtil responseUtil;

    @Transactional
    public TokensAndUserIdDto loginWithPhoneAndPassword(PhoneAndPasswordDto phoneAndPasswordDto) {
        User user = validateUser(phoneAndPasswordDto.getMobilePhone());

        boolean validPassword = new BCryptPasswordEncoder().matches(phoneAndPasswordDto.getPassword(), user.getPassword());
        if (validPassword) {
            Pair<String, String> tokens = tokenService.generateAccessAndRefreshTokens(user);
            return responseUtil.createTokensAndUserIdDto(tokens, user.getId());
        } else {
            log.error("Incorrect password for user with id='{}'", user.getId());
            throw new IncorrectPasswordException();
        }
    }

    @Transactional(noRollbackFor = {
            IncorrectPinCodeException.class,
            ApplicationIsLockedException.class
    })
    public TokensAndUserIdDto loginWithPhoneAndPinCode(PhoneAndPinCodeDto phoneAndPinCodeDto) {
        User user = validateUser(phoneAndPinCodeDto.getMobilePhone());

        String pinCode = phoneAndPinCodeDto.getPinCode();
        int attemptCounter = user.getAttemptCounter();
        if (attemptCounter < MAX_LIMIT - 1) {
            userUtil.checkPinCodeBeforeMaxLimitExceeded(user, pinCode);
        } else {
            boolean matchesPinCode = userUtil.matchesPinCode(user, pinCode);
            if (!matchesPinCode) {
                user.setAttemptCounter(0);
                user.setStatus(UserStatusType.BLOCKED);

                ClientStatusChangedEvent.Payload payload = eventPayloadUtil.createUserBlockedEventPayload(user);
                ClientStatusChangedEvent event = ClientStatusChangedEvent.builder().build();
                eventUtil.populateEventFields(event, EventType.CLIENT_STATUS_CHANGED, user.getId() ,
                        user.getVersion(), user.getId(), payload);

                eventSender.send(event);
                eventRepository.save(event);

                refreshTokenRepository.deleteByUserId(user.getId());

                throw new ApplicationIsLockedException();
            }
        }

        userUtil.resetAttemptCounter(user);
        Pair<String, String> tokens = tokenService.generateAccessAndRefreshTokens(user);
        return responseUtil.createTokensAndUserIdDto(tokens, user.getId());
    }

    private User validateUser(String mobilePhone) {
        User user = userUtil.findUserByMobilePhone(mobilePhone);
        userUtil.checkUserRights(user);
        userUtil.checkIfUserIsBlocked(user);
        return user;
    }
}
