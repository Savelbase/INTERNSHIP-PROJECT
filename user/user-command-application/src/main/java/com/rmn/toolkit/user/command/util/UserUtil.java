package com.rmn.toolkit.user.command.util;

import com.rmn.toolkit.user.command.dto.request.ChangePasswordDto;
import com.rmn.toolkit.user.command.exception.forbidden.DifferentPasswordException;
import com.rmn.toolkit.user.command.exception.forbidden.DifferentUserIdException;
import com.rmn.toolkit.user.command.exception.notfound.UserNotFoundException;
import com.rmn.toolkit.user.command.model.User;
import com.rmn.toolkit.user.command.repository.UserRepository;
import com.rmn.toolkit.user.command.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserUtil {
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;

    public User findUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User with id='{}' not found", userId);
                    throw new UserNotFoundException(userId);
                });
    }

    public void checkIfTokenUserIdMatchUserId(String userId) {
        if (!Objects.equals(securityUtil.getCurrentUserId(), userId)){
            log.error("User with id='{}' doesn't match userId in token", userId);
            throw new DifferentUserIdException();
        }
    }

    public String encodeValue(String value) {
        return new BCryptPasswordEncoder().encode(value);
    }

    public void checkIfOldPasswordMatch(ChangePasswordDto passwordDto, User user) {
        if (!new BCryptPasswordEncoder().matches(passwordDto.getOldPassword(), user.getPassword())){
            log.error("Passwords doesn't match");
            throw new DifferentPasswordException();
        }
    }
}
