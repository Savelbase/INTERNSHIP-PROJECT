package com.rmn.toolkit.cards.command.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.rmn.toolkit.cards.command.event.card.*;
import com.rmn.toolkit.cards.command.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.cards.command.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.cards.command.event.user.UserDeletedEvent;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClientRegisteredEvent.Payload.class, name = "CLIENT_REGISTERED"),
        @JsonSubTypes.Type(value = ClientStatusChangedEvent.Payload.class, name = "CLIENT_STATUS_CHANGED"),
        @JsonSubTypes.Type(value = UserDeletedEvent.Payload.class, name = "USER_DELETED"),
        @JsonSubTypes.Type(value = CardOrderCreatedEvent.Payload.class, name = "CARD_ORDER_CREATED"),
        @JsonSubTypes.Type(value = CardOrderStatusChangedEvent.Payload.class, name = "CARD_ORDER_STATUS_CHANGED"),
        @JsonSubTypes.Type(value = CardCreatedEvent.Payload.class, name = "CARD_CREATED"),
        @JsonSubTypes.Type(value = CardStatusChangedEvent.Payload.class, name = "CARD_STATUS_CHANGED"),
        @JsonSubTypes.Type(value = CardDailyLimitChangedEvent.Payload.class, name = "CARD_DAILY_LIMIT_CHANGED")
})
@Data
@NoArgsConstructor
public class EventPayload {
}
