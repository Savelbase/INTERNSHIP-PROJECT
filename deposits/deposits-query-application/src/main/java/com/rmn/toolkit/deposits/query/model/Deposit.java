package com.rmn.toolkit.deposits.query.model;

import com.rmn.toolkit.deposits.query.model.iface.Versionable;
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
public class Deposit implements Versionable {
    @Id
    @NotNull
    private String id;

    @NotNull
    private String clientId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    private DepositProduct depositProduct;

    @NotNull
    private BigDecimal depositAmount;

    @NotNull
    private ZonedDateTime startDepositPeriod;

    @NotNull
    private ZonedDateTime endDepositPeriod;

    @NotNull
    private boolean revocable;

    @NotNull
    private Integer version;
}
