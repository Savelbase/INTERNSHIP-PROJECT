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
public class MobilePhoneDto {
    @NotNull
    @Schema(example = "9012345678")
    @Pattern(
            regexp = RegexConstants.MOBILE_PHONE_REGEX,
            message = "Mobile phone can contain only 10 digits"
    )
    private String mobilePhone;
}
