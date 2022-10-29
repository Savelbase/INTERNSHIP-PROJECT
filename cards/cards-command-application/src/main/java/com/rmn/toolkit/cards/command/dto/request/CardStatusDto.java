package com.rmn.toolkit.cards.command.dto.request;

import com.rmn.toolkit.cards.command.model.type.CardStatusType;
import com.rmn.toolkit.cards.command.util.validator.EnumNamePattern;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardStatusDto {
    @NotNull
    @Schema(example = "8d3a68a1-5919-2378-bc20-839fae2480aa")
    private String cardId;

    @NotNull
    @Schema(example = "ACTIVE|BLOCKED")
    @EnumNamePattern(regexp = "ACTIVE|BLOCKED")
    private CardStatusType status;
}
