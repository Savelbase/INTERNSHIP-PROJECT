package com.rmn.toolkit.credits.query.event.credit;

import com.rmn.toolkit.credits.query.event.Event;
import com.rmn.toolkit.credits.query.event.EventPayload;
import com.rmn.toolkit.credits.query.model.type.CreditOrderStatusType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

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
