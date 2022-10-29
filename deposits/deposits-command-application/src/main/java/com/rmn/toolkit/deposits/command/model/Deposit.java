package com.rmn.toolkit.deposits.command.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "deposits")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Deposit {

    @Id
    @NotNull
    private String id;

    @NotNull
    private String clientId;

    @OneToOne(fetch = FetchType.LAZY)
    private Account account;

    @OneToOne(fetch = FetchType.LAZY)
    private DepositProduct depositProduct;

    @NotNull
    private BigDecimal depositAmount;

    @NotNull
    private ZonedDateTime endDepositPeriod;

    @NotNull
    private ZonedDateTime startDepositPeriod;

    @NotNull
    private boolean revocable;

    @NotNull
    private Integer version;
}
