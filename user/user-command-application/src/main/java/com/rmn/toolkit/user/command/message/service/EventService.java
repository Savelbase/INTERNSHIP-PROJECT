package com.rmn.toolkit.user.command.message.service;

import com.rmn.toolkit.user.command.event.Event;
import com.rmn.toolkit.user.command.event.EventPayload;
import com.rmn.toolkit.user.command.event.client.ApprovedBankClientEvent;
import com.rmn.toolkit.user.command.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.user.command.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.user.command.event.user.NotificationChangeStateEvent;
import com.rmn.toolkit.user.command.event.user.UserDeletedEvent;
import com.rmn.toolkit.user.command.event.user.UserEditedEvent;
import com.rmn.toolkit.user.command.exception.notfound.ClientNotFoundException;
import com.rmn.toolkit.user.command.exception.notfound.UserNotFoundException;
import com.rmn.toolkit.user.command.message.projector.ClientProjector;
import com.rmn.toolkit.user.command.message.projector.UserProjector;
import com.rmn.toolkit.user.command.model.Client;
import com.rmn.toolkit.user.command.model.DeletedUser;
import com.rmn.toolkit.user.command.model.User;
import com.rmn.toolkit.user.command.repository.ClientRepository;
import com.rmn.toolkit.user.command.repository.DeletedUserRepository;
import com.rmn.toolkit.user.command.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventService {
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final ClientProjector clientProjector;
    private final UserProjector userProjector;
    private final DeletedUserRepository deletedUserRepository;

    @Transactional
    public void handle(ClientRegisteredEvent event) {
        Client client = clientProjector.project(event);
        User user = userProjector.project(event);
        clientRepository.save(client);
        userRepository.save(user);
        log.info("Client with id {} saved to db after receiving event", client.getId());
    }

    @Transactional
    public void handle(UserEditedEvent event) {
        User user = getUserFromEvent(event);
        userProjector.project(event,user);
        userRepository.save(user);
        log.info("User with id {} changed in db after receiving event", user.getId());
    }

    @Transactional
    public void handle(NotificationChangeStateEvent event) {
        User oldUser = getUserFromEvent(event);
        User newUser = userProjector.project(event,oldUser);
        userRepository.save(newUser);
        log.info("User with id {} changed in db after receiving event", newUser.getId());
    }

    @Transactional
    public void handle(ClientStatusChangedEvent event) {
        Client client = getClientFromEvent(event);
        clientProjector.project(event, client);
        clientRepository.save(client);
        log.info("Client with id {} changed status after event received", client.getId());
    }

    @Transactional
    public void handle(ApprovedBankClientEvent event) {
        Client client = getClientFromEvent(event);
        clientProjector.project(event, client);
        clientRepository.save(client);
        log.info("Client with id {} approved as bank-client after event received", client.getId());
    }

    @Transactional
    public void handle(UserDeletedEvent event) {
        User user = getUserFromEvent(event);
        Client client = getClientFromEvent(event);

        DeletedUser deletedUser = userProjector.project(event, user, client);
        deletedUserRepository.save(deletedUser);
        clientRepository.deleteById(client.getId());
        log.info("User with id {} deleted after event received", deletedUser.getId());
    }

    private User getUserFromEvent(Event<? extends EventPayload> event) {
        String userId = event.getEntityId();
        return userRepository.findById(userId).orElseThrow(() -> {
            log.info("User with id {} does not exist", userId);
            throw new UserNotFoundException(userId);
        });
    }

    private Client getClientFromEvent(Event<? extends EventPayload> event) {
        String clientId = event.getEntityId();
        return clientRepository.findById(clientId).orElseThrow(() -> {
            log.info("Client with id {} does not exist", clientId);
            throw new ClientNotFoundException(clientId);
        });
    }
}
