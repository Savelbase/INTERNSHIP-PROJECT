package com.rmn.toolkit.user.query.dto.response;

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

    public static SuccessResponse getGeneric() {
        return new SuccessResponse("Operation completed successfully");
    }
}
