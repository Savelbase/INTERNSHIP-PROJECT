package com.rmn.toolkit.cards.command.service;

import com.rmn.toolkit.cards.command.dto.request.CardDailyLimitDto;
import com.rmn.toolkit.cards.command.dto.request.CardStatusDto;
import com.rmn.toolkit.cards.command.event.EventType;
import com.rmn.toolkit.cards.command.event.card.CardCreatedEvent;
import com.rmn.toolkit.cards.command.event.card.CardDailyLimitChangedEvent;
import com.rmn.toolkit.cards.command.event.card.CardStatusChangedEvent;
import com.rmn.toolkit.cards.command.message.EventSender;
import com.rmn.toolkit.cards.command.model.Account;
import com.rmn.toolkit.cards.command.model.Card;
import com.rmn.toolkit.cards.command.model.CardOrder;
import com.rmn.toolkit.cards.command.model.CardRequisites;
import com.rmn.toolkit.cards.command.util.CardProductUtil;
import com.rmn.toolkit.cards.command.util.CardUtil;
import com.rmn.toolkit.cards.command.util.EventPayloadUtil;
import com.rmn.toolkit.cards.command.util.EventUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardUtil cardUtil;
    private final CardProductUtil cardProductUtil;
    private final EventPayloadUtil eventPayloadUtil;
    private final EventUtil eventUtil;
    private final EventSender eventSender;

    public void createCard(CardOrder cardOrder) {
        cardProductUtil.findCardProductById(cardOrder.getCardProductId());
        Account account = cardUtil.createAccount(cardOrder.getClientId());
        CardRequisites cardRequisites = cardUtil.createCardRequisites(cardOrder, account);
        Card card = cardUtil.createCard(cardOrder, cardRequisites);

        CardCreatedEvent.Payload payload = eventPayloadUtil.createCardCreatedEvent(account, cardRequisites, card);
        CardCreatedEvent event = CardCreatedEvent.builder().build();
        eventUtil.populateEventFields(event, EventType.CARD_CREATED, card.getId(), card.getVersion(), card.getClientId(), payload);
        eventSender.send(event);
    }

    @Transactional
    public void changeCardStatusById(CardStatusDto cardStatusDto) {
        Card card = cardUtil.findCardById(cardStatusDto.getCardId());
        card.setStatus(cardStatusDto.getStatus());

        CardStatusChangedEvent.Payload payload = eventPayloadUtil.createCardStatusChangedEvent(card);
        CardStatusChangedEvent event = CardStatusChangedEvent.builder().build();
        eventUtil.populateEventFields(event, EventType.CARD_STATUS_CHANGED, card.getId(),
                card.getVersion() + 1, card.getClientId(), payload);
        eventSender.send(event);
    }

    @Transactional
    public void changeCardDailyLimit(CardDailyLimitDto cardDailyLimitDto) {
        Card card = cardUtil.findCardById(cardDailyLimitDto.getCardId());
        card.setDailyLimitSum(cardDailyLimitDto.getDailyLimitSum());

        CardDailyLimitChangedEvent.Payload payload = eventPayloadUtil.createCardLimitChangedEvent(card);
        CardDailyLimitChangedEvent event = CardDailyLimitChangedEvent.builder().build();
        eventUtil.populateEventFields(event, EventType.CARD_DAILY_LIMIT_CHANGED, card.getId(),
                card.getVersion() + 1, card.getClientId(), payload);
        eventSender.send(event);
    }
}
