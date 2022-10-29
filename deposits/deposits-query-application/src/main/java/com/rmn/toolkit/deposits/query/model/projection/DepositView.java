package com.rmn.toolkit.deposits.query.model.projection;

import com.rmn.toolkit.deposits.query.dto.success.DepositDto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public interface DepositView {

    DepositProductView getDepositProduct();
    BigDecimal getDepositAmount();
    ZonedDateTime getEndDepositPeriod();
    ZonedDateTime getStartDepositPeriod();

}
