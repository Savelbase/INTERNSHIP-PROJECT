package com.rmn.toolkit.credits.command.dto.response.success;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditOrderIdResponse {
    @Schema(example = "0d3a68a1-5919-4914-bc20-839fae2480ac")
    private String creditOrderId;
}
