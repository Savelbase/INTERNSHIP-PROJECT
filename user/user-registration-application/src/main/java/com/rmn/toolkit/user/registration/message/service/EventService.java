package com.rmn.toolkit.user.registration.message.service;

import com.rmn.toolkit.user.registration.event.Event;
import com.rmn.toolkit.user.registration.event.EventPayload;
import com.rmn.toolkit.user.registration.event.client.BankClientCreatedEvent;
import com.rmn.toolkit.user.registration.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.user.registration.event.user.UserDeletedEvent;
import com.rmn.toolkit.user.registration.exception.notfound.ClientNotFoundException;
import com.rmn.toolkit.user.registration.message.projector.ClientProjector;
import com.rmn.toolkit.user.registration.model.Client;
import com.rmn.toolkit.user.registration.model.Role;
import com.rmn.toolkit.user.registration.model.type.RoleType;
import com.rmn.toolkit.user.registration.repository.ClientRepository;
import com.rmn.toolkit.user.registration.util.RoleUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventService {
    private final ClientProjector clientProjector;
    private final ClientRepository clientRepository;
    private final RoleUtil roleUtil;

    @Transactional
    public void handle(ClientRegisteredEvent event) {
        Client client = clientProjector.project(event);
        clientRepository.save(client);
        log.info("Client with id='{}' created after consuming event", client.getId());
    }

    @Transactional
    public void handle(BankClientCreatedEvent event) {
        Role role = roleUtil.findRoleByName(RoleType.BANK_CLIENT.name());
        Client client = clientProjector.project(event, role.getId());
        clientRepository.save(client);
        log.info("Bank Client with id='{}' created after consuming event", client.getId());
    }

    @Transactional
    public void handle(UserDeletedEvent event) {
        Client client = getClientFromEvent(event);
        clientRepository.deleteById(client.getId());
        log.info("Client with id='{}' deleted after consuming event", client.getId());
    }

    private Client getClientFromEvent(Event<? extends EventPayload> event) {
        String clientId = event.getEntityId();
        return clientRepository.findById(clientId)
                .orElseThrow(() -> {
                    log.info("Client with id {} not found", clientId);
                    throw new ClientNotFoundException(clientId);
                });
    }
}
