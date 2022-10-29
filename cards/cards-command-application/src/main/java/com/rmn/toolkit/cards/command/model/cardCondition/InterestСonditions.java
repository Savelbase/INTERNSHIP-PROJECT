package com.rmn.toolkit.cards.command.model.cardCondition;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InterestСonditions {
    Integer interestOnCashWithdrawal;
    BigDecimal maximumAmountWithoutInterest ;
}
