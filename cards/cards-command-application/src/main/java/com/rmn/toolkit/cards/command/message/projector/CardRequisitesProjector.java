package com.rmn.toolkit.cards.command.message.projector;

import com.rmn.toolkit.cards.command.event.card.CardCreatedEvent;
import com.rmn.toolkit.cards.command.model.CardRequisites;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardRequisitesProjector {
    public CardRequisites project(CardCreatedEvent event) {
        return event.getPayload().getCardRequisites();
    }
}
