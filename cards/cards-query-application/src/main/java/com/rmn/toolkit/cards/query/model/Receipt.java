package com.rmn.toolkit.cards.query.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "receipts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Receipt {
    @Id
    @NotNull
    private String id;

    @NotNull
    private String transactionType;

    @NotNull
    private ZonedDateTime transactionTime;

    @NotNull
    private BigDecimal transactionAmount;

    @NotNull
    private String transactionLocation;

    @NotNull
    private String additionalInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    private Recipient recipient;
}
