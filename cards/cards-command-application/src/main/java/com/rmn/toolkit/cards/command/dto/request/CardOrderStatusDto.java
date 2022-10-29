package com.rmn.toolkit.cards.command.dto.request;

import com.rmn.toolkit.cards.command.model.type.CardOrderStatusType;
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
public class CardOrderStatusDto {
    @NotNull
    @Schema(example = "85e73ea5-c904-45d5-8787-1bda24d5db9e")
    private String cardOrderId;

    @NotNull
    @Schema(example = "APPROVED|DENIED")
    @EnumNamePattern(regexp = "APPROVED|DENIED")
    private CardOrderStatusType status;
}

