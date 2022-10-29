package com.rmn.toolkit.cards.query.model.cardCondition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankService implements Serializable {
    Integer numberOfFreeMonth ;
    BigDecimal monthlyCost;
}
