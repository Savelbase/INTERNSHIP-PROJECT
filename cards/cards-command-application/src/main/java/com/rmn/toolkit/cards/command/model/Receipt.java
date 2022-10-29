package com.rmn.toolkit.cards.command.model;

import com.rmn.toolkit.cards.command.model.type.TransactionDescriptionType;
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
    private String cardId;

    @NotNull
    private String recipientId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionDescriptionType transactionType;

    @NotNull
    private ZonedDateTime transactionTime;

    @NotNull
    private BigDecimal transactionAmount;

    @NotNull
    private String transactionLocation;

    @NotNull
    private String additionalInfo;
}
