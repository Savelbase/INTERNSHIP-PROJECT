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
public class PassportNumberDto {
    @NotNull
    @Schema(example = "7777000000")
    @Pattern(
            regexp = RegexConstants.PASSPORT_NUMBER_REGEX,
            message = "Passport number can contain {Upper/Lower 'Eng/Ru' letters, digits and special symbols}"
    )
    private String passportNumber;
}
