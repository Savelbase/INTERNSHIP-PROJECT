package com.rmn.toolkit.credits.command.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.rmn.toolkit.credits.command.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.credits.command.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.credits.command.event.credit.CreditCreatedEvent;
import com.rmn.toolkit.credits.command.event.credit.CreditOrderCreatedEvent;
import com.rmn.toolkit.credits.command.event.credit.CreditOrderStatusChangedEvent;
import com.rmn.toolkit.credits.command.event.user.UserDeletedEvent;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreditOrderCreatedEvent.Payload.class, name = "CREDIT_ORDER_CREATED"),
        @JsonSubTypes.Type(value = CreditCreatedEvent.Payload.class, name = "CREDIT_CREATED"),
        @JsonSubTypes.Type(value = CreditOrderStatusChangedEvent.Payload.class, name = "CREDIT_ORDER_STATUS_CHANGED"),
        @JsonSubTypes.Type(value = ClientRegisteredEvent.Payload.class, name = "CLIENT_REGISTERED"),
        @JsonSubTypes.Type(value = ClientStatusChangedEvent.Payload.class, name = "CLIENT_STATUS_CHANGED"),
        @JsonSubTypes.Type(value = UserDeletedEvent.Payload.class, name = "USER_DELETED")
})
@Data
@NoArgsConstructor
public class EventPayload {
}
