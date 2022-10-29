package com.rmn.toolkit.deposits.command.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.rmn.toolkit.deposits.command.event.client.ClientRegisteredEvent;
import com.rmn.toolkit.deposits.command.event.client.ClientStatusChangedEvent;
import com.rmn.toolkit.deposits.command.event.deposit.DepositCreatedEvent;
import com.rmn.toolkit.deposits.command.event.user.UserDeletedEvent;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClientRegisteredEvent.class, name = "CLIENT_REGISTERED"),
        @JsonSubTypes.Type(value = ClientStatusChangedEvent.class, name = "CLIENT_STATUS_CHANGED"),
        @JsonSubTypes.Type(value = UserDeletedEvent.class, name = "USER_DELETED"),
        @JsonSubTypes.Type(value = DepositCreatedEvent.class, name = "DEPOSIT_CREATED"),
})
@Data
@NoArgsConstructor
public class EventPayload {
}
