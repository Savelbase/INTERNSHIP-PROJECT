package com.rmn.toolkit.cards.command.model.cardCondition;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BankService {
    Integer numberOfFreeMonth ;
    BigDecimal monthlyCost;
}
