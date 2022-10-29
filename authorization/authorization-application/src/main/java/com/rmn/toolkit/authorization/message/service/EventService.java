package com.rmn.toolkit.authorization.message.service;

import com.rmn.toolkit.authorization.event.Event;
import com.rmn.toolkit.authorization.event.EventPayload;
import com.rmn.toolkit.authorization.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.authorization.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.authorization.event.user.UserDeletedEvent;
import com.rmn.toolkit.authorization.event.user.UserEditedEvent;
import com.rmn.toolkit.authorization.exception.notfound.UserNotFoundException;
import com.rmn.toolkit.authorization.message.projector.UserProjector;
import com.rmn.toolkit.authorization.model.User;
import com.rmn.toolkit.authorization.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventService {
    private final UserProjector userProjector;
    private final UserRepository userRepository;

    @Transactional
    public void handle(ClientRegisteredEvent event) {
        User user = userProjector.project(event);
        userRepository.save(user);
        log.info("User with id='{}' created after consuming event", user.getId());
    }

    @Transactional
    public void handle(UserEditedEvent event) {
        User user = getUserFromEvent(event);
        userProjector.project(event, user);
        userRepository.save(user);
        log.info("User with id='{}' edited after consuming event", user.getId());
    }

    @Transactional
    public void handle(ClientStatusChangedEvent event) {
        User user = getUserFromEvent(event);
        userProjector.project(event, user);
        userRepository.save(user);
        log.info("User with id='{}' got changed status after consuming event", user.getId());
    }

    @Transactional
    public void handle(UserDeletedEvent event) {
        User user = getUserFromEvent(event);
        userProjector.project(event, user);
        userRepository.deleteById(user.getId());
        log.info("User with id='{}' deleted after consuming event", user.getId());
    }

    private User getUserFromEvent(Event<? extends EventPayload> event) {
        String userId = event.getEntityId();
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.info("User with id {} not found", userId);
                    throw new UserNotFoundException(userId);
                });
    }
}
