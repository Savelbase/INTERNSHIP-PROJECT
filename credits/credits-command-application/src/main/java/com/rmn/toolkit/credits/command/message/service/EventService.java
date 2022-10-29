package com.rmn.toolkit.credits.command.message.service;

import com.rmn.toolkit.credits.command.event.Event;
import com.rmn.toolkit.credits.command.event.EventPayload;
import com.rmn.toolkit.credits.command.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.credits.command.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.credits.command.event.credit.CreditCreatedEvent;
import com.rmn.toolkit.credits.command.event.credit.CreditOrderCreatedEvent;
import com.rmn.toolkit.credits.command.event.credit.CreditOrderStatusChangedEvent;
import com.rmn.toolkit.credits.command.event.user.UserDeletedEvent;
import com.rmn.toolkit.credits.command.exception.notfound.ClientNotFoundException;
import com.rmn.toolkit.credits.command.exception.notfound.CreditOrderNotFoundException;
import com.rmn.toolkit.credits.command.message.projector.*;
import com.rmn.toolkit.credits.command.model.*;
import com.rmn.toolkit.credits.command.model.type.CreditOrderStatusType;
import com.rmn.toolkit.credits.command.repository.*;
import com.rmn.toolkit.credits.command.service.CreditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventService {
    private final CreditOrderProjector creditOrderProjector;
    private final PayGraphProjector payGraphProjector;
    private final AccountProjector accountProjector ;
    private final ClientProjector clientProjector;
    private final CreditProjector creditProjector;
    private final CreditOrderRepository creditOrderRepository;
    private final PayGraphRepository payGraphRepository;
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;
    private final CreditRepository creditRepository;
    private final CreditService creditService;

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

        if (creditOrder.getStatus().equals(CreditOrderStatusType.APPROVED)) {
            creditService.createCredit(creditOrder);
        }
    }

    @Transactional
    public void handle(CreditCreatedEvent event) {
        Account account = accountProjector.project(event);
        accountRepository.save(account);
        log.info("Account with id {} saved to db after event received", account.getId());

        Credit credit = creditProjector.project(event);
        creditRepository.save(credit);
        log.info("Credit with id {} saved to db after event received", credit.getId());

        PayGraph payGraph = payGraphProjector.project(event);
        payGraphRepository.save(payGraph);
        log.info("PayGraph with id {} saved to db after event received", payGraph.getId());
    }

    @Transactional
    public void handle(ClientRegisteredEvent event) {
        Client client = clientProjector.project(event);
        clientRepository.save(client);
        log.info("Client with id {} saved after event received", client.getId());
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
