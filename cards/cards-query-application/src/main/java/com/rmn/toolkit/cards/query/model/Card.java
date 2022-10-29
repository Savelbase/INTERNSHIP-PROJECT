package com.rmn.toolkit.cards.query.model;

import com.rmn.toolkit.cards.query.model.iface.Versionable;
import com.rmn.toolkit.cards.query.model.type.CardStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "cards")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Card implements Versionable {
    @Id
    @NotNull
    private String id;

    @NotNull
    private BigDecimal cardBalance;

    @NotNull
    private BigDecimal dailyLimitSum;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CardStatusType status;

    @NotNull
    private Integer version;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    private CardProduct cardProduct;

    @OneToOne(fetch = FetchType.LAZY)
    private CardRequisites cardRequisites;

    @OneToMany(mappedBy = "card", fetch = FetchType.LAZY)
    private List<Receipt> receipts;
}
