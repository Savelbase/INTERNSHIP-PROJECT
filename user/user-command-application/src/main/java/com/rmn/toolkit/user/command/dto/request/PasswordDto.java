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
public class PasswordDto {
    @NotNull
    @Schema(example = "Qwerty1!")
    @Pattern(
            regexp = RegexConstants.PASSWORD_REGEX,
            message = "Password must contain at least one character from at least three character groups " +
                    "{Upper/Lower 'Eng' letters, digits, special symbols}"
    )
    private String password;
}
