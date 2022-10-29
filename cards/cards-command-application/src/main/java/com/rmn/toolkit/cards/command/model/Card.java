package com.rmn.toolkit.cards.command.model;

import com.rmn.toolkit.cards.command.model.iface.Versionable;
import com.rmn.toolkit.cards.command.model.type.CardStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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
    private String clientId;

    @NotNull
    private String cardProductId;

    @NotNull
    private String cardRequisitesId;

    @NotNull
    private BigDecimal cardBalance;

    @NotNull
    private BigDecimal dailyLimitSum;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CardStatusType status;

    @NotNull
    private Integer version;
}
