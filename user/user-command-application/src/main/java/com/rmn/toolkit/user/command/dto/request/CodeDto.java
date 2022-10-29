package com.rmn.toolkit.user.command.dto.request;

import com.rmn.toolkit.user.command.dto.RegexConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeDto {
    @NotNull
    @Schema(example = "123456")
    @Pattern(
            regexp = RegexConstants.CODE_REGEX,
            message = "Code can contain only 6 digits"
    )
    private String code;
}
