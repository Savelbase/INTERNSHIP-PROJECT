package com.rmn.toolkit.cards.query.dto.request;

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
public class TransactionTypeDto {
    @NotNull
    @Schema(example = "8d3a68a1-5919-2378-bc20-839fae2480aa")
    private String cardId;

    @NotNull
    @Schema(example = "PAYMENT|TRANSFER|ANOTHER|ALL")
    @Pattern(regexp = RegexConstants.TRANSACTION_DESCRIPTION_REGEX,
            message = "Transaction type can contain " +
                    "{Upper/Lower 'Eng/Ru' letters, digits and special symbols} in range [3-12]")
    private String transactionType;
}

