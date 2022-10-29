package com.rmn.toolkit.authorization.util;

import com.rmn.toolkit.authorization.exception.locked.UserStatusBlockedException;
import com.rmn.toolkit.authorization.exception.notfound.UserNotFoundException;
import com.rmn.toolkit.authorization.exception.unauthorized.IncorrectPinCodeException;
import com.rmn.toolkit.authorization.model.Role;
import com.rmn.toolkit.authorization.model.User;
import com.rmn.toolkit.authorization.model.type.UserStatusType;
import com.rmn.toolkit.authorization.repository.UserRepository;
import com.rmn.toolkit.authorization.security.AuthorityType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserUtil {
    private static final int MAX_LIMIT = 3;

    private final UserRepository userRepository;
    private final RoleUtil roleUtil;

    public User findUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User with id='{}' not found", userId);
                    throw new UserNotFoundException(userId);
                });
    }

    public void checkUserRights(User user) {
        Role role = roleUtil.findRoleById(user.getRoleId());
        if (Arrays.stream(role.getAuthorities()).noneMatch(AuthorityType.AUTHORIZATION::equals)) {
            log.info("User with id='{}' hasn't got enough rights for authorization", user.getId());
            throw new AccessDeniedException(
                    String.format("User with id='%s' hasn't got enough rights for authorization", user.getId()));
        }
    }

    public User findUserByMobilePhone(String mobilePhone) {
        return userRepository.findByMobilePhone(mobilePhone)
                .orElseThrow(() -> {
                    log.error("User with mobile phone='{}' not found", mobilePhone);
                    throw new UserNotFoundException(mobilePhone, true);
                });
    }

    public void checkPinCodeBeforeMaxLimitExceeded(User user, String pinCode) {
        boolean validPinCode = matchesPinCode(user, pinCode);
        if (!validPinCode) {
            incrementAttemptCounterOfPinCode(user);
            userRepository.save(user);
            int remainingAttempts = MAX_LIMIT - user.getAttemptCounter();
            throw new IncorrectPinCodeException(remainingAttempts);
        }
    }

    public boolean matchesPinCode(User user, String pinCode) {
        return new BCryptPasswordEncoder().matches(pinCode, user.getPinCode());
    }

    public void incrementAttemptCounterOfPinCode(User user) {
        user.setAttemptCounter(user.getAttemptCounter() + 1);
    }

    public void checkIfUserIsBlocked(User user) {
        if (UserStatusType.BLOCKED.equals(user.getStatus())) {
            log.error("User status is blocked");
            throw new UserStatusBlockedException();
        }
    }

    public void resetAttemptCounter(User user) {
        user.setAttemptCounter(0);
        userRepository.save(user);
    }
}
