package com.rmn.toolkit.cards.query.model;

import com.rmn.toolkit.cards.query.model.type.CardType;
import com.rmn.toolkit.cards.query.model.type.Currency;
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

    @OneToOne
    @JoinColumn(name = "card_conditions_dictionary_id")
    private CardConditionsDictionary cardConditionsDictionary;
    @OneToOne
    @JoinColumn(name = "card_conditions_id")
    private CardConditions cardConditions;
}
