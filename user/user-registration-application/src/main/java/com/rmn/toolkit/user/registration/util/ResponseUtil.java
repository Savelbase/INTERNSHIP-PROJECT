package com.rmn.toolkit.user.registration.util;

import com.rmn.toolkit.user.registration.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.user.registration.dto.response.error.GeneralMessageErrorResponse;
import com.rmn.toolkit.user.registration.dto.response.success.AccessTokenResponse;
import com.rmn.toolkit.user.registration.dto.response.success.VerificationCodeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ResponseUtil {
    @Value("${authentication.token.type}")
    private String tokenType;
    @Value("${authentication.token.accessTokenExpirationSec}")
    private Integer accessTokenExpirationSec;
    @Value("${authentication.verificationCode.expirationSec}")
    private Integer verificationCodeExpirationSec;

    public GeneralErrorTypeErrorResponse createGeneralErrorTypeErrorResponse(GeneralErrorTypeErrorResponse.ErrorType errorType) {
        return GeneralErrorTypeErrorResponse.builder()
                .dateTime(Instant.now())
                .errorType(errorType)
                .build();
    }

    public GeneralMessageErrorResponse createGeneralMessageErrorResponse(String message) {
        return GeneralMessageErrorResponse.builder()
                .dateTime(Instant.now())
                .message(message)
                .build();
    }

    public AccessTokenResponse createAccessToken(String accessToken) {
        return AccessTokenResponse.builder()
                .accessToken(accessToken)
                .tokenType(tokenType)
                .accessTokenExpirationTime(accessTokenExpirationSec)
                .build();
    }

    public VerificationCodeResponse createVerificationCode(String verificationCode) {
        return VerificationCodeResponse.builder()
                .verificationCode(verificationCode)
                .verificationCodeExpirationTime(verificationCodeExpirationSec)
                .build();
    }
}
