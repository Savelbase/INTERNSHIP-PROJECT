package com.rmn.toolkit.cards.query.message.projector;

import com.rmn.toolkit.cards.query.event.card.CardCreatedEvent;
import com.rmn.toolkit.cards.query.model.*;
import com.rmn.toolkit.cards.query.util.CardProductUtil;
import com.rmn.toolkit.cards.query.util.ClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardRequisitesProjector {
    private final ClientUtil clientUtil;
    private final CardProductUtil cardProductUtil;

    public CardRequisites project(CardCreatedEvent event) {
        var payload = event.getPayload();
        Account account = payload.getAccount();
        Client client = clientUtil.findClientById(event.getAuthorId());
        CardProduct cardProduct = cardProductUtil.findCardProductById(payload.getCardProductId());

        CardRequisites cardRequisites = payload.getCardRequisites();
        cardRequisites.setAccount(account);
        cardRequisites.setClient(client);
        cardRequisites.setCardProduct(cardProduct);
        return cardRequisites;
    }
}
