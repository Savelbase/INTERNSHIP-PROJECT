package com.rmn.toolkit.cards.query.util;

import com.rmn.toolkit.cards.query.dto.response.success.CardRequisitesDto;
import com.rmn.toolkit.cards.query.exception.notfound.CardRequisitesNotFoundException;
import com.rmn.toolkit.cards.query.model.projection.AccountView;
import com.rmn.toolkit.cards.query.model.projection.CardProductView;
import com.rmn.toolkit.cards.query.model.projection.CardRequisitesView;
import com.rmn.toolkit.cards.query.model.projection.ClientView;
import com.rmn.toolkit.cards.query.repository.CardRequisitesRepository;
import com.rmn.toolkit.cryptography.RSAUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CardRequisitesUtil {
    private final CardRequisitesRepository cardRequisitesRepository;
    private final RSAUtils rsaUtils;

    public CardRequisitesView findCardRequisitesViewByCardId(String cardId) {
        return cardRequisitesRepository.getByCard_Id(cardId)
                .orElseThrow(() -> {
                    log.error("Card requisites by cardId='{}' not found", cardId);
                    throw new CardRequisitesNotFoundException(cardId);
                });
    }

    @SneakyThrows
    public CardRequisitesDto createCardRequisitesDto(CardRequisitesView cardRequisitesView) {
        AccountView accountView = cardRequisitesView.getAccount();
        ClientView clientView = cardRequisitesView.getClient();
        CardProductView cardProductView = cardRequisitesView.getCardProduct();
        return CardRequisitesDto.builder()
                .id(cardRequisitesView.getId())
                .cardName(cardProductView.getCardName())
                .cardNumber(rsaUtils.decryptedByPrivateKey(cardRequisitesView.getCardNumber()))
                .currency(cardProductView.getCurrency())
                .endCardPeriod(cardRequisitesView.getEndCardPeriod())
                .accountNumber(rsaUtils.decryptedByPrivateKey(accountView.getAccountNumber()))
                .cardHolderFirstName(clientView.getFirstName())
                .cardHolderLastName(clientView.getLastName())
                .build();
    }
}
