package com.rmn.toolkit.user.registration.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcceptRBSSRulesDto {
    @NotNull(message = "RBSS_SHOULD_BE_ACCEPTED")
    @Schema(example = "true")
    @Pattern(
            regexp = RegexConstants.TRUE_VALUE_REGEX,
            message = "RBSS_SHOULD_BE_ACCEPTED"
    )
    private String acceptedValue;
}
