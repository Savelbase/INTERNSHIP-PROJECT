package com.rmn.toolkit.cards.command.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDailyLimitDto {
    @NotNull
    @Schema(example = "8d3a68a1-5919-2378-bc20-839fae2480aa")
    private String cardId;

    @NotNull(message = "Daily limit sum can contain only digits")
    @Schema(example = "5000")
    @Digits(integer = 7, fraction = 2,
            message = "Daily limit sum can contain only digits")
    @DecimalMin(value = "00.00", message = "Available min value 00.00")
    @DecimalMax(value = "1000000", message = "Available max value 1000000")
    private BigDecimal dailyLimitSum;

    @NotNull(message = "CONDITIONS_MUST_BE_ACCEPTED")
    @Schema(example = "true")
    @Pattern(regexp = RegexConstants.TRUE_VALUE_REGEX,
            message = "CONDITIONS_MUST_BE_ACCEPTED")
    private String acceptedValue;
}
