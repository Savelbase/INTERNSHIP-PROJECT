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
public class VerificationCodeDto {
    @NotNull
    @Schema(example = "123456")
    @Pattern(
            regexp = RegexConstants.VERIFICATION_CODE_REGEX,
            message = "Verification code can contain only 6 digits"
    )
    private String verificationCode;
}
