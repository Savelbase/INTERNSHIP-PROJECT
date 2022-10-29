package com.rmn.toolkit.user.registration.dto.response.success;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RulesTextResponse {
    @Schema(example = "Text of rules")
    private String text;
}
