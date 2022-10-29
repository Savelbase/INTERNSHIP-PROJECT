package com.rmn.toolkit.deposits.command.event.deposit;

import com.rmn.toolkit.deposits.command.event.Event;
import com.rmn.toolkit.deposits.command.event.EventPayload;
import com.rmn.toolkit.deposits.command.model.Account;
import com.rmn.toolkit.deposits.command.model.DepositProduct;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@DiscriminatorValue(value = "DEPOSIT_CREATED")
@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class DepositCreatedEvent extends Event<DepositCreatedEvent.Payload> {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = true)
    public static class Payload extends EventPayload {
        private Account account;
        private DepositProduct depositProduct;
        private BigDecimal depositAmount;
        private ZonedDateTime endDepositPeriod;
        private ZonedDateTime startDepositPeriod;
        private boolean isRevocable;
    }
}
