package com.rmn.toolkit.credits.command.model;

import com.rmn.toolkit.credits.command.model.type.CreditOrderStatusType;
import com.rmn.toolkit.credits.command.model.iface.Versionable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditOrder implements Versionable {
    @Id
    @NotNull
    private String id;

    @NotNull
    private String clientId;

    @NotNull
    private BigDecimal creditAmount;

    @NotNull
    private LocalDate startCreditPeriod;

    @NotNull
    private LocalDate endCreditPeriod;

    @NotNull
    private BigDecimal averageMonthlyIncome;

    @NotNull
    private BigDecimal averageMonthlyExpenses;

    @NotNull
    private String employerTin;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CreditOrderStatusType status;

    @NotNull
    private String creditProductName;

    @NotNull
    private Integer version;
}
