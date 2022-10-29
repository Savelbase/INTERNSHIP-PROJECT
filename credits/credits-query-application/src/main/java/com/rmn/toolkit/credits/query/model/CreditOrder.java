package com.rmn.toolkit.credits.query.model;

import com.rmn.toolkit.credits.query.model.type.CreditOrderStatusType;
import com.rmn.toolkit.credits.query.model.iface.Versionable;
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
    private CreditOrderStatusType status;

    @NotNull
    private String clientId;

    @NotNull
    private Integer version;

    @ManyToOne(fetch = FetchType.LAZY)
    private CreditDictionary creditProduct;
}
