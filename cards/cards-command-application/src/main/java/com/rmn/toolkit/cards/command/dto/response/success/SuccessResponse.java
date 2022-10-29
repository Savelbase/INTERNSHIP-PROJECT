package com.rmn.toolkit.cards.command.dto.response.success;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponse {
    @Schema(example = "Operation completed successfully")
    private String message;
}
