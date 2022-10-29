package com.rmn.toolkit.credits.query.model;

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
@Table(name = "credits")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Credit implements Versionable {
    @Id
    @NotNull
    private String id;

    @NotNull
    private String agreementNumber;

    @NotNull
    private BigDecimal creditAmount;

    @NotNull
    private BigDecimal debt;

    @NotNull
    private LocalDate startCreditPeriod;

    @NotNull
    private LocalDate endCreditPeriod;

    @NotNull
    private LocalDate dateToPay;

    @NotNull
    private String employerTin;

    @NotNull
    private Integer version;

    @ManyToOne(fetch = FetchType.LAZY)
    private CreditDictionary creditProduct;

    @OneToOne(fetch = FetchType.LAZY)
    private Account account;

    @OneToOne(fetch = FetchType.LAZY)
    private PayGraph payGraph;
}
