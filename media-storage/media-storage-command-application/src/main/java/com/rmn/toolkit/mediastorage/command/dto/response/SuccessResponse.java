package com.rmn.toolkit.mediastorage.command.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SuccessResponse {
    @Schema(example = "Operation completed successfully")
    protected String message;

    public static SuccessResponse getGeneric() {
        return new SuccessResponse("Operation completed successfully");
    }
}
