package com.rmn.toolkit.deposits.command.message.service;


import com.rmn.toolkit.deposits.command.event.Event;
import com.rmn.toolkit.deposits.command.event.EventPayload;
import com.rmn.toolkit.deposits.command.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.deposits.command.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.deposits.command.event.deposit.DepositCreatedEvent;
import com.rmn.toolkit.deposits.command.event.user.UserDeletedEvent;
import com.rmn.toolkit.deposits.command.exception.notfound.ClientNotFoundException;

import com.rmn.toolkit.deposits.command.message.projector.ClientProjector;
import com.rmn.toolkit.deposits.command.message.projector.DepositProjector;
import com.rmn.toolkit.deposits.command.model.Client;
import com.rmn.toolkit.deposits.command.model.Deposit;
import com.rmn.toolkit.deposits.command.repository.ClientRepository;
import com.rmn.toolkit.deposits.command.repository.DepositRepository;
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
    private final DepositProjector depositProjector;
    private final DepositRepository depositRepository;

    @Transactional
    public void handle(DepositCreatedEvent event) {
        Deposit deposit = depositProjector.project(event);
        depositRepository.save(deposit);
        log.info("Deposit with id {} saved to db after receiving event", deposit.getId());
    }

    @Transactional
    public void handle(ClientRegisteredEvent event) {
        Client client = clientProjector.project(event);
        clientRepository.save(client);
        log.info("Client with id {} saved to db after receiving event", client.getId());
    }

    @Transactional
    public void handle(ClientStatusChangedEvent event) {
        Client client = getClientFromEvent(event);
        clientProjector.project(event, client);
        clientRepository.save(client);
        log.info("Client with id {} changed status after event received", client.getId());
    }

    @Transactional
    public void handle(UserDeletedEvent event) {
        Client client = getClientFromEvent(event);
        clientRepository.deleteById(client.getId());
        log.info("Client with id='{}' deleted after consuming event", client.getId());
    }

    private Client getClientFromEvent(Event<? extends EventPayload> event) {
        String clientId = event.getEntityId();
        return clientRepository.findById(clientId).orElseThrow(() -> {
            log.info("Client with id {} does not exist", clientId);
            throw new ClientNotFoundException(clientId);
        });
    }
}
