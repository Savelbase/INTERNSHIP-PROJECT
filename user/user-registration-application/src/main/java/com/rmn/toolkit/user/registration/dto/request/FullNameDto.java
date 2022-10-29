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
public class FullNameDto {
    @NotNull
    @Schema(example = "Ivan")
    @Pattern(
            regexp = RegexConstants.USER_NAME_REGEX,
            message = "First name can contain {Upper/Lower 'Eng/Ru' letters, digits and special symbols}"
    )
    private String firstName;

    @NotNull
    @Schema(example = "Ivanov")
    @Pattern(
            regexp = RegexConstants.USER_NAME_REGEX,
            message = "Last name can contain {Upper/Lower 'Eng/Ru' letters, digits and special symbols}"
    )
    private String lastName;

    @Schema(example = "Ivanovich")
    private String middleName;
}
