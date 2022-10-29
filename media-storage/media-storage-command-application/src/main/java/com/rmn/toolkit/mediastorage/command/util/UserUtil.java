package com.rmn.toolkit.mediastorage.command.util;

import com.rmn.toolkit.mediastorage.command.exception.notfound.UserNotFoundException;
import com.rmn.toolkit.mediastorage.command.exception.locked.UserStatusBlockedException;
import com.rmn.toolkit.mediastorage.command.model.User;
import com.rmn.toolkit.mediastorage.command.model.type.UserStatusType;
import com.rmn.toolkit.mediastorage.command.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserUtil {
    private final UserRepository userRepository;

    public User findUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User with id='{}' not found", userId);
                    throw new UserNotFoundException(userId);
                });
    }

    public void checkIfUserIsBlocked(User user) {
        if (UserStatusType.BLOCKED.equals(user.getStatus())) {
            log.error("User status is blocked");
            throw new UserStatusBlockedException();
        }
    }
}
