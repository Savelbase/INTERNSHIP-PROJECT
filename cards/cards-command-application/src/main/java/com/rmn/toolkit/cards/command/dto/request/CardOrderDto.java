package com.rmn.toolkit.cards.command.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardOrderDto {
    @NotNull
    @Schema(example = "85e73ea5-c904-45d5-8787-1bda24d5db9e")
    private String cardProductId;

    @NotNull(message = "CONDITIONS_MUST_BE_ACCEPTED")
    @Schema(example = "true")
    @Pattern(regexp = RegexConstants.TRUE_VALUE_REGEX,
            message = "CONDITIONS_MUST_BE_ACCEPTED")
    private String acceptedValue;
}
