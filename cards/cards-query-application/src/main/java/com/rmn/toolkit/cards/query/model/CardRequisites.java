package com.rmn.toolkit.cards.query.model;

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
    private String cardNumber;

    @NotNull
    private ZonedDateTime endCardPeriod;

    @OneToOne(fetch = FetchType.LAZY)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    private CardProduct cardProduct;

    @OneToOne(mappedBy = "cardRequisites", fetch = FetchType.LAZY)
    private Card card;
}
