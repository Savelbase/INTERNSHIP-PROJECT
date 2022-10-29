package com.rmn.toolkit.cards.query.model.cardCondition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashBack implements Serializable {
    Integer percent ;
    BigDecimal minimumPurchaseAmount;
}
