package com.rmn.toolkit.credits.command.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditOrderDto {
    @NotNull
    @Schema(example = "1000")
    @DecimalMin(value = "0.00", inclusive = false,
            message = "Decimal value should be above 0")
    private BigDecimal creditAmount;

    @NotNull
    @Schema(example = "6")
    @Min(value = 1, message = "Credit period should be min for 1 month")
    private Integer monthPeriod;

    @NotNull
    @Schema(example = "1000")
    @DecimalMin(value = "0.00", inclusive = false,
            message = "Decimal value should be above 0")
    private BigDecimal averageMonthlyIncome;

    @NotNull
    @Schema(example = "800")
    @DecimalMin(value = "0.00", inclusive = false,
            message = "Decimal value should be above 0")
    private BigDecimal averageMonthlyExpenses;

    @NotNull
    @Schema(example = "7727563778")
    private String employerTin;

    @NotNull
    @Schema(example = "Consumer Credit")
    private String creditProductName;
}
