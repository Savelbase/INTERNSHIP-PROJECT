package com.rmn.toolkit.credits.command.event.credit;

import com.rmn.toolkit.credits.command.model.type.CreditOrderStatusType;
import com.rmn.toolkit.credits.command.event.Event;
import com.rmn.toolkit.credits.command.event.EventPayload;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "CREDIT_ORDER_STATUS_CHANGED")
@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CreditOrderStatusChangedEvent extends Event<CreditOrderStatusChangedEvent.Payload> {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = true)
    public static class Payload extends EventPayload {
        private CreditOrderStatusType status;
    }
}
