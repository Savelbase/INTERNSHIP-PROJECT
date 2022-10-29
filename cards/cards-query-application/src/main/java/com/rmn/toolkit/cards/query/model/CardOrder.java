package com.rmn.toolkit.cards.query.model;

import com.rmn.toolkit.cards.query.model.iface.Versionable;
import com.rmn.toolkit.cards.query.model.type.CardOrderStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "card_orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardOrder implements Versionable {
    @Id
    @NotNull
    private String id;
    @NotNull
    private String clientId;
    @NotNull
    private String cardProductId;
    @NotNull
    @Enumerated(EnumType.STRING)
    private CardOrderStatusType status;
    @NotNull
    private Integer version;
}
