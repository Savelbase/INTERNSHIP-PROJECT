package com.rmn.toolkit.user.command.util;

import com.rmn.toolkit.user.command.dto.response.success.AccessTokenResponse;
import com.rmn.toolkit.user.command.dto.response.success.VerificationCodeWithTokenResponse;
import com.rmn.toolkit.user.command.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.user.command.dto.response.error.GeneralMessageErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@RequiredArgsConstructor
@Component
public class ResponseUtil {
    @Value("${authentication.verificationCode.expirationSec}")
    private Integer verificationCodeExpirationSec;
    @Value("${authentication.token.type}")
    private String tokenType;
    @Value("${authentication.token.accessTokenExpirationSec}")
    private Integer accessTokenExpirationSec;

    public VerificationCodeWithTokenResponse getVerificationCodeWithTokenResponse(String verificationCode , String token){
        return VerificationCodeWithTokenResponse.builder()
                .verificationCode(verificationCode)
                .verificationCodeExpirationTime(verificationCodeExpirationSec)
                .accessToken(token)
                .tokenType(tokenType)
                .accessTokenExpirationTime(accessTokenExpirationSec)
                .build();
    }

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

}
