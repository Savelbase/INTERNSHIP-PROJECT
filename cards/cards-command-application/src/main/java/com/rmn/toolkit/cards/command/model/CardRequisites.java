package com.rmn.toolkit.cards.command.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
@Table(name = "card_requisites")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardRequisites {
    @Id
    @NotNull
    private String id;

    @NotNull
    private String clientId;

    @NotNull
    private String cardProductId;

    @NotNull
    private String accountId;

    @NotNull
    private String cardNumber;

    @NotNull
    private ZonedDateTime endCardPeriod;
}
