package com.rmn.toolkit.cards.command.util;

import com.rmn.toolkit.cards.command.exception.notfound.CardNotFoundException;
import com.rmn.toolkit.cards.command.model.Account;
import com.rmn.toolkit.cards.command.model.Card;
import com.rmn.toolkit.cards.command.model.CardOrder;
import com.rmn.toolkit.cards.command.model.CardRequisites;
import com.rmn.toolkit.cards.command.model.type.CardStatusType;
import com.rmn.toolkit.cards.command.repository.CardRepository;
import com.rmn.toolkit.cryptography.RSAUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;


@Component
@RequiredArgsConstructor
@Slf4j
public class CardUtil {
    private static final int CARD_VALIDITY_IN_YEARS = 4;
    private static final int VERSION = 1;

    private final CardRepository cardRepository;
    private final RSAUtils rsaUtils;

    public Card findCardById(String id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Card with id='{}' not found", id);
                    throw new CardNotFoundException(id);
                });
    }

    public Card createCard(CardOrder cardOrder, CardRequisites cardRequisites) {
        return Card.builder()
                .id(UUID.randomUUID().toString())
                .clientId(cardOrder.getClientId())
                .cardProductId(cardOrder.getCardProductId())
                .cardRequisitesId(cardRequisites.getId())
                .cardBalance(BigDecimal.ZERO)
                .dailyLimitSum(BigDecimal.ZERO)
                .status(CardStatusType.BLOCKED)
                .version(VERSION)
                .build();
    }

    @SneakyThrows
    public Account createAccount(String clientId) {
        return Account.builder()
                .id(UUID.randomUUID().toString())
                .clientId(clientId)
                .accountNumber(rsaUtils.encryptedDataByPublicKey(UUID.randomUUID().toString()))
                .build();
    }

    @SneakyThrows
    public CardRequisites createCardRequisites(CardOrder cardOrder, Account account) {
        return CardRequisites.builder()
                .id(UUID.randomUUID().toString())
                .accountId(account.getId())
                .clientId(cardOrder.getClientId())
                .cardProductId(cardOrder.getCardProductId())
                .cardNumber(rsaUtils.encryptedDataByPublicKey(UUID.randomUUID().toString()))
                .endCardPeriod(ZonedDateTime.now().plusYears(CARD_VALIDITY_IN_YEARS))
                .build();
    }

}
