package com.rmn.toolkit.cards.query.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardStatementsPeriodDto {
    @NotNull
    @Schema(example = "8d3a68a1-5919-2378-bc20-839fae2480aa")
    private String cardId;

    @NotNull
    @Schema(example = "2022-07-10")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startPeriod;

    @NotNull
    @Schema(example = "2022-07-20")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endPeriod;
}

