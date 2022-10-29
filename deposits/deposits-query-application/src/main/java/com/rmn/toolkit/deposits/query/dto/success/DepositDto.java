package com.rmn.toolkit.deposits.query.dto.success;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepositDto {

    private String depositName;
    private BigDecimal depositAmount;
    private String depositPeriod;
}
