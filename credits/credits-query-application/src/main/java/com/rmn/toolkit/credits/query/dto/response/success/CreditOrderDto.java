package com.rmn.toolkit.credits.query.dto.response.success;

import com.rmn.toolkit.credits.query.model.type.CreditOrderStatusType;
import com.rmn.toolkit.credits.query.model.type.Currency;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditOrderDto {
    private String id;
    private CreditOrderStatusType status;
    private String creditProductName;
    private BigDecimal creditAmount;
    private Currency currency;
}
