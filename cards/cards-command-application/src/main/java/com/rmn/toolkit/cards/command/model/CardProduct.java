package com.rmn.toolkit.cards.command.model;

import com.rmn.toolkit.cards.command.model.type.CardType;
import com.rmn.toolkit.cards.command.model.type.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "card_products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardProduct {
    @Id
    @NotNull
    private String id;

    @NotNull
    private String cardName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CardType type;

    @NotNull
    @OneToOne(mappedBy = "cardProduct", cascade = CascadeType.ALL)
    private CardConditions cardConditions ;
}
