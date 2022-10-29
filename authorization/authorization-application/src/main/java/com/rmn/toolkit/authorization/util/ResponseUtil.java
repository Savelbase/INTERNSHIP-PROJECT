package com.rmn.toolkit.authorization.util;

import com.rmn.toolkit.authorization.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.authorization.dto.response.error.GeneralMessageErrorResponse;
import com.rmn.toolkit.authorization.dto.response.success.TokensAndUserIdDto;
import com.rmn.toolkit.authorization.dto.response.success.TokensResponse;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ResponseUtil {
    @Value("${authentication.token.type}")
    private String tokenType;
    @Value("${authentication.token.accessTokenExpirationSec}")
    private Integer accessTokenExpirationSec;

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

    public TokensAndUserIdDto createTokensAndUserIdDto(Pair<String, String> tokens, String userId) {
        return TokensAndUserIdDto.builder()
                .accessToken(tokens.getLeft())
                .refreshToken(tokens.getRight())
                .userId(userId)
                .build();
    }

    public TokensResponse createTokensResponse(TokensAndUserIdDto tokensAndUserIdDto) {
        return TokensResponse.builder()
                .accessToken(tokensAndUserIdDto.getAccessToken())
                .tokenType(tokenType)
                .expirationTime(accessTokenExpirationSec)
                .refreshToken(tokensAndUserIdDto.getRefreshToken())
                .userId(tokensAndUserIdDto.getUserId())
                .build();
    }
}