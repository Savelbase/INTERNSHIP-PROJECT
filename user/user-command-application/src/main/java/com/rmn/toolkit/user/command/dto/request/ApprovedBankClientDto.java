package com.rmn.toolkit.user.command.dto.request;

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
public class ApprovedBankClientDto {
    @NotNull
    @Schema(example = "0d3a68a1-5919-4914-bc20-839fae2480ac")
    private String clientId;

    @NotNull(message = "BANK_CLIENT_SHOULD_BE_APPROVED")
    @Schema(example = "true")
    @Pattern(
            regexp = "^true$",
            message = "BANK_CLIENT_SHOULD_BE_APPROVED"
    )
    private String approvedValue;
}
