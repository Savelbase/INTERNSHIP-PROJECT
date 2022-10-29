package com.rmn.toolkit.credits.query.dto.response.success;

import com.rmn.toolkit.credits.query.model.type.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditProductDto {
    private String id;
    private String name;
    private Double percent;
    private Currency currency;
    private BigDecimal minCreditAmount;
    private BigDecimal maxCreditAmount;
    private Integer minMonthPeriod;
    private Integer maxMonthPeriod;
    private boolean earlyRepayment;
    private boolean guarantors;
    private boolean incomeStatement;
}
