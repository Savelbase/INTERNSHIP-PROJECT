package com.rmn.toolkit.cards.query.event.card;

import com.rmn.toolkit.cards.query.event.Event;
import com.rmn.toolkit.cards.query.event.EventPayload;
import com.rmn.toolkit.cards.query.model.type.CardOrderStatusType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CardOrderStatusChangedEvent extends Event<CardOrderStatusChangedEvent.Payload> {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = true)
    public static class Payload extends EventPayload {
        private CardOrderStatusType status;
    }
}