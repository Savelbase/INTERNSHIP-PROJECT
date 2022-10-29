package com.rmn.toolkit.cards.command.dto.response.success;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardOrderIdResponse {
    @Schema(example = "85e73ea5-c904-45d5-8787-1bda24d5db9e")
    private String cardOrderId;
}
