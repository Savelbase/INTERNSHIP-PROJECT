package com.rmn.toolkit.user.registration.dto.response.success;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerificationCodeResponse {
    @Schema(example = "123456")
    private String verificationCode;

    @Schema(example = "2min", description = "Verification code expiration time is 2 min")
    private Integer verificationCodeExpirationTime;
}
