package com.rmn.toolkit.credits.query.event.credit;

import com.rmn.toolkit.credits.query.event.Event;
import com.rmn.toolkit.credits.query.event.EventPayload;
import com.rmn.toolkit.credits.query.model.Account;
import com.rmn.toolkit.credits.query.model.Payment;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CreditCreatedEvent extends Event<CreditCreatedEvent.Payload> {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode(callSuper = true)
    public static class Payload extends EventPayload {
        private Account account;
        private String creditProductId;
        private String agreementNumber;
        private BigDecimal creditAmount;
        private BigDecimal debt;
        private LocalDate startCreditPeriod;
        private LocalDate endCreditPeriod;
        private LocalDate dateToPay;
        private String employerTin;
        private String payGraphId;
        private List<Payment> paymentList;
    }
}
