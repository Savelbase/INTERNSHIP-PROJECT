package com.rmn.toolkit.credits.command.event.credit;

import com.rmn.toolkit.credits.command.event.Event;
import com.rmn.toolkit.credits.command.event.EventPayload;
import com.rmn.toolkit.credits.command.model.Account;
import com.rmn.toolkit.credits.command.model.Payment;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@DiscriminatorValue(value = "CREDIT_CREATED")
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
