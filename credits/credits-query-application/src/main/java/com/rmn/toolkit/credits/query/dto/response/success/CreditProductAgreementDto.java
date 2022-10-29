package com.rmn.toolkit.credits.query.dto.response.success;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditProductAgreementDto {
    @Schema(example = "Agreement text")
    private String agreementText;
}
