package com.rmn.toolkit.credits.query.model;

import com.rmn.toolkit.credits.query.model.type.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "credit_dictionary")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditDictionary {
    @Id
    @NotNull
    private String id;

    @NotNull
    private String name;

    @NotNull
    private Double percent;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Currency currency;

    @NotNull
    private BigDecimal minCreditAmount;

    @NotNull
    private BigDecimal maxCreditAmount;

    @NotNull
    private Integer minMonthPeriod;

    @NotNull
    private Integer maxMonthPeriod;

    @NotNull
    private String agreementText;

    @NotNull
    private boolean earlyRepayment;

    @NotNull
    private boolean guarantors;

    @NotNull
    private boolean incomeStatement;

    @OneToMany(mappedBy = "creditProduct", fetch = FetchType.LAZY)
    private List<CreditOrder> creditOrders;

    @OneToMany(mappedBy = "creditProduct", fetch = FetchType.LAZY)
    private List<Credit> credits;
}

