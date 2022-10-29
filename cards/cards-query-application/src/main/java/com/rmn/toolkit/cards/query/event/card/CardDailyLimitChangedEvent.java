package com.rmn.toolkit.cards.query.event.card;

import com.rmn.toolkit.cards.query.event.Event;
import com.rmn.toolkit.cards.query.event.EventPayload;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CardDailyLimitChangedEvent extends Event<CardDailyLimitChangedEvent.Payload> {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = true)
    public static class Payload extends EventPayload {
        private BigDecimal dailyLimitSum;
    }
}
