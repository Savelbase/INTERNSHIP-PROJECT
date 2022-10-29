package com.rmn.toolkit.credits.query.message.service;

import com.rmn.toolkit.credits.query.event.Event;
import com.rmn.toolkit.credits.query.event.EventPayload;
import com.rmn.toolkit.credits.query.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.credits.query.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.credits.query.event.credit.CreditCreatedEvent;
import com.rmn.toolkit.credits.query.event.credit.CreditOrderCreatedEvent;
import com.rmn.toolkit.credits.query.event.credit.CreditOrderStatusChangedEvent;
import com.rmn.toolkit.credits.query.message.projector.*;
import com.rmn.toolkit.credits.query.model.*;
import com.rmn.toolkit.credits.query.repository.*;
import com.rmn.toolkit.credits.query.event.user.UserDeletedEvent;
import com.rmn.toolkit.credits.query.model.CreditOrder;
import com.rmn.toolkit.credits.query.repository.ClientRepository;
import com.rmn.toolkit.credits.query.repository.CreditOrderRepository;
import com.rmn.toolkit.credits.query.exception.notfound.ClientNotFoundException;
import com.rmn.toolkit.credits.query.exception.notfound.CreditOrderNotFoundException;
import com.rmn.toolkit.credits.query.message.projector.ClientProjector;
import com.rmn.toolkit.credits.query.message.projector.CreditOrderProjector;
import com.rmn.toolkit.credits.query.model.Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventService {
    private final CreditOrderRepository creditOrderRepository;
    private final CreditOrderProjector creditOrderProjector;
    private final PayGraphProjector payGraphProjector;
    private final AccountProjector accountProjector;
    private final CreditProjector creditProjector;
    private final ClientProjector clientProjector;
    private final PayGraphRepository payGraphRepository;
    private final AccountRepository accountRepository;
    private final CreditRepository creditRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public void handle(CreditOrderCreatedEvent event) {
        CreditOrder creditOrder = creditOrderProjector.project(event);
        creditOrderRepository.save(creditOrder);
        log.info("Credit order with id {} saved to db after receiving event", creditOrder.getId());
    }

    @Transactional
    public void handle(CreditOrderStatusChangedEvent event) {
        CreditOrder creditOrder = getCreditOrderFromEvent(event);
        creditOrderProjector.project(event, creditOrder);
        creditOrderRepository.save(creditOrder);
        log.info("Credit order with id {} changed status after event received", creditOrder.getId());
    }

    @Transactional
    public void handle(CreditCreatedEvent event) {
        Account account = accountProjector.project(event);
        accountRepository.save(account);
        log.info("Account with id {} saved to db after event received", account.getId());

        PayGraph payGraph = payGraphProjector.project(event);
        payGraphRepository.save(payGraph);
        log.info("PayGraph with id {} saved to db after receiving event", payGraph.getId());

        Credit credit = creditProjector.project(event, payGraph);
        creditRepository.save(credit);
        log.info("Credit with id {} saved to db after receiving event", credit.getId());
    }

    @Transactional
    public void handle(ClientRegisteredEvent event) {
        Client client = clientProjector.project(event);
        clientRepository.save(client);
        log.info("Client with id {} saved after receiving event", client.getId());
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

    private CreditOrder getCreditOrderFromEvent(Event<? extends EventPayload> event) {
        String creditOrderId = event.getEntityId();
        return creditOrderRepository.findById(creditOrderId).orElseThrow(() -> {
            log.info("Credit order with id {} does not exist", creditOrderId);
            throw new CreditOrderNotFoundException(creditOrderId);
        });
    }

    private Client getClientFromEvent(Event<? extends EventPayload> event) {
        String creditOrderId = event.getEntityId();
        return clientRepository.findById(creditOrderId).orElseThrow(() -> {
            log.info("Client with id {} does not exist", creditOrderId);
            throw new ClientNotFoundException(creditOrderId);
        });
    }
}
