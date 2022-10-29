package com.rmn.toolkit.credits.query.dto.response.success;

import com.rmn.toolkit.credits.query.model.Payment;
import com.rmn.toolkit.credits.query.model.type.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditDto {
    private String id;
    private String creditProductName;
    private BigDecimal creditAmount;
    private Currency currency;
    private Double percent;
    private BigDecimal debt;
    private String agreementNumber;
    private String accountNumber;
    private LocalDate startCreditPeriod;
    private LocalDate endCreditPeriod;
    private LocalDate dateToPay;
    private List<Payment> paymentList;
}
