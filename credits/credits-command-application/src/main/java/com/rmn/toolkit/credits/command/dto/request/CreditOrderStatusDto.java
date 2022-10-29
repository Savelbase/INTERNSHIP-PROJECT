package com.rmn.toolkit.credits.command.dto.request;

import com.rmn.toolkit.credits.command.model.type.CreditOrderStatusType;
import com.rmn.toolkit.credits.command.util.validator.EnumNamePattern;
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
public class CreditOrderStatusDto {
    @NotNull
    @Schema(example = "85e73ea5-c904-45d5-8787-1bda24d5db9e")
    private String creditOrderId;

    @Schema(example = "APPROVED|DENIED")
    @EnumNamePattern(regexp = "APPROVED|DENIED")
    private CreditOrderStatusType statusType ;
}

