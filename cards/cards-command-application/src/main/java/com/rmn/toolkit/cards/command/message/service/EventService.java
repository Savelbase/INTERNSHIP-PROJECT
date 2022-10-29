package com.rmn.toolkit.cards.command.message.service;

import com.rmn.toolkit.cards.command.event.Event;
import com.rmn.toolkit.cards.command.event.EventPayload;
import com.rmn.toolkit.cards.command.event.card.*;
import com.rmn.toolkit.cards.command.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.cards.command.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.cards.command.event.user.UserDeletedEvent;
import com.rmn.toolkit.cards.command.exception.notfound.CardNotFoundException;
import com.rmn.toolkit.cards.command.exception.notfound.CardOrderNotFoundException;
import com.rmn.toolkit.cards.command.exception.notfound.ClientNotFoundException;
import com.rmn.toolkit.cards.command.message.projector.*;
import com.rmn.toolkit.cards.command.model.*;
import com.rmn.toolkit.cards.command.model.type.CardOrderStatusType;
import com.rmn.toolkit.cards.command.repository.*;
import com.rmn.toolkit.cards.command.service.CardService;
import com.rmn.toolkit.cards.command.util.CardUtil;
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
    private final CardOrderProjector cardOrderProjector;
    private final CardOrderRepository cardOrderRepository;
    private final CardService cardService;

    private final CardUtil cardUtil;
    private final CardProjector cardProjector;
    private final CardRepository cardRepository;
    private final AccountProjector accountProjector;
    private final AccountRepository accountRepository;
    private final CardRequisitesProjector cardRequisitesProjector;
    private final CardRequisitesRepository cardRequisitesRepository;

    @Transactional
    public void handle(CardOrderCreatedEvent event) {
        CardOrder cardOrder = cardOrderProjector.project(event);
        cardOrderRepository.save(cardOrder);
        log.info("Card order with id {} saved to db after receiving event", cardOrder.getId());
    }

    @Transactional
    public void handle(CardOrderStatusChangedEvent event) {
        CardOrder cardOrder = getCardOrderFromEvent(event);
        cardOrderProjector.project(event, cardOrder);
        cardOrderRepository.save(cardOrder);
        log.info("Card order with id {} changed status after receiving event", cardOrder.getId());

        if (cardOrder.getStatus().equals(CardOrderStatusType.APPROVED)) {
            cardService.createCard(cardOrder);
        }
    }

    @Transactional
    public void handle(CardCreatedEvent event) {
        Account account = accountProjector.project(event);
        accountRepository.save(account);
        log.info("Account with id {} saved to db after event received", account.getId());

        CardRequisites cardRequisites = cardRequisitesProjector.project(event);
        cardRequisitesRepository.save(cardRequisites);
        log.info("Card requisites with id {} saved to db after event received", cardRequisites.getId());

        Card card = cardProjector.project(event);
        cardRepository.save(card);
        log.info("Card with id {} saved to db after event received", card.getId());
    }

    @Transactional
    public void handle(CardStatusChangedEvent event) {
        Card card = getCardFromEvent(event);
        cardProjector.project(event, card);
        cardRepository.save(card);
        log.info("Card with id {} changed status after receiving event", card.getId());
    }

    @Transactional
    public void handle(CardDailyLimitChangedEvent event) {
        Card card = getCardFromEvent(event);
        cardProjector.project(event, card);
        cardRepository.save(card);
        log.info("Card with id {} changed daily limit sum after receiving event", card.getId());
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

    private CardOrder getCardOrderFromEvent(Event<? extends EventPayload> event) {
        String cardOrderId = event.getEntityId();
        return cardOrderRepository.findById(cardOrderId).orElseThrow(() -> {
            log.info("Card order with id {} does not exist", cardOrderId);
            throw new CardOrderNotFoundException(cardOrderId);
        });
    }

    private Card getCardFromEvent(Event<? extends EventPayload> event) {
        String cardId = event.getEntityId();
        return cardRepository.findById(cardId).orElseThrow(() -> {
            log.info("Card with id {} does not exist", cardId);
            throw new CardNotFoundException(cardId);
        });
    }
}
