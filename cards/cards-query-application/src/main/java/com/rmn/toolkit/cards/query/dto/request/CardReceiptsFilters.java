package com.rmn.toolkit.cards.query.dto.request;

import com.rmn.toolkit.cards.query.model.type.PaymentOperationType;
import com.rmn.toolkit.cards.query.model.type.TransactionType;
import com.rmn.toolkit.cards.query.util.validator.EnumNamePattern;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardReceiptsFilters {
    private static final String MIN_SUM = "00.01";
    private static final String MAX_SUM = "1000000";
    private static final int DEFAULT_PERIOD_IN_DAYS = 10;

    @NotNull
    @Schema(example = "8d3a68a1-5919-2378-bc20-839fae2480aa")
    private String cardId;

    @NotNull
    @Schema(example = "INCOME|EXPENSE|ALL")
    @EnumNamePattern(regexp = "INCOME|EXPENSE|ALL", message = "NO_SUCH_TYPE")
    private PaymentOperationType operationType;

    @NotNull
    @Schema(example = "PAYMENT|TRANSFER|ANOTHER|ALL")
    @EnumNamePattern(regexp = "PAYMENT|TRANSFER|ANOTHER|ALL", message = "NO_SUCH_TYPE")
    private TransactionType transactionType;

    @NotNull
    @Schema(example = MIN_SUM)
    @Digits(integer = 7, fraction = 2,
            message = "Operation sum can contain only digits in range [" + MIN_SUM + "-" + MAX_SUM + "]")
    @DecimalMin(value = MIN_SUM, message = "Available min value " + MIN_SUM)
    @DecimalMax(value = MAX_SUM, message = "Available max value " + MAX_SUM)
    private BigDecimal minOperationSum;

    @NotNull
    @Schema(example = MAX_SUM)
    @Digits(integer = 7, fraction = 2,
            message = "Operation sum can contain only digits in range [" + MIN_SUM + "-" + MAX_SUM + "]")
    @DecimalMin(value = MIN_SUM, message = "Available min value " + MIN_SUM)
    @DecimalMax(value = MAX_SUM, message = "Available max value " + MAX_SUM)
    private BigDecimal maxOperationSum;

    @NotNull
    @Schema(example = "2022-07-10")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startPeriod;

    @NotNull
    @Schema(example = "2022-07-20")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endPeriod;
}

