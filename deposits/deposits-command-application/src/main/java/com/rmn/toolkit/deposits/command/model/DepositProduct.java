package com.rmn.toolkit.deposits.command.model;

import com.rmn.toolkit.deposits.command.model.type.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "deposit_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepositProduct {
    @Id
    @NotNull
    private String id;

    @NotNull
    private String depositName;

    @NotNull
    private BigDecimal depositRate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DepositTermType termType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DepositRenewableType renewableType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DepositRefundableType refundableType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DepositExpendableType expendableType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DepositCapitalizationType capitalizationType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DepositTargetType depositTargetType;
}
