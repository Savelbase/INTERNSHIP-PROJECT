package com.rmn.toolkit.user.query.message.service;

import com.rmn.toolkit.user.query.event.Event;
import com.rmn.toolkit.user.query.event.EventPayload;
import com.rmn.toolkit.user.query.event.client.ApprovedBankClientEvent;
import com.rmn.toolkit.user.query.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.user.query.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.user.query.event.user.NotificationChangeStateEvent;
import com.rmn.toolkit.user.query.event.user.UserDeletedEvent;
import com.rmn.toolkit.user.query.event.user.UserEditedEvent;
import com.rmn.toolkit.user.query.exception.notfound.ClientNotFoundException;
import com.rmn.toolkit.user.query.exception.notfound.UserNotFoundException;
import com.rmn.toolkit.user.query.message.projector.ClientProjector;
import com.rmn.toolkit.user.query.message.projector.UserProjector;
import com.rmn.toolkit.user.query.model.Client;
import com.rmn.toolkit.user.query.model.User;
import com.rmn.toolkit.user.query.repo.ClientRepository;
import com.rmn.toolkit.user.query.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Slf4j
public class EventService {
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final ClientProjector clientProjector;
    private final UserProjector userProjector;

    @Transactional
    public void handle(ClientRegisteredEvent event) {
        Client client = clientProjector.project(event);
        User user = userProjector.project(event);
        clientRepository.save(client);
        userRepository.save(user);
        log.info("Client with id {} saved after receiving event", client.getId());
    }

    @Transactional
    public void handle(UserEditedEvent event) {
        User user = getUserFromEvent(event);
        userProjector.project(event, user);
        userRepository.save(user);
        log.info("User with id {} changed after event received", user.getId());
    }

    @Transactional
    public void handle(NotificationChangeStateEvent event) {
        User user = getUserFromEvent(event);
        userProjector.project(event, user);
        userRepository.save(user);
        log.info("User with id {} changed after event received", user.getId());
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
        userProjector.project(event, user);

        clientRepository.deleteById(client.getId());
        log.info("Client with id {} deleted after event received", client.getId());
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
