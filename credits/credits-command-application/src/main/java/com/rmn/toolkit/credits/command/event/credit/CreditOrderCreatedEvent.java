package com.rmn.toolkit.credits.command.event.credit;

import com.rmn.toolkit.credits.command.model.type.CreditOrderStatusType;
import com.rmn.toolkit.credits.command.event.Event;
import com.rmn.toolkit.credits.command.event.EventPayload;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@DiscriminatorValue(value = "CREDIT_ORDER_CREATED")
@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CreditOrderCreatedEvent extends Event<CreditOrderCreatedEvent.Payload> {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = true)
    public static class Payload extends EventPayload {
        private BigDecimal creditAmount;
        private LocalDate startCreditPeriod;
        private LocalDate endCreditPeriod;
        private BigDecimal averageMonthlyIncome;
        private BigDecimal averageMonthlyExpenses;
        private String employerTin;
        private CreditOrderStatusType status;
        private String creditProductName;
    }
}
