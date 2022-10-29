package com.rmn.toolkit.authorization.dto.response.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralErrorTypeErrorResponse {
    @Schema(example = "2022-04-16T23:07:59.441143700Z")
    private Instant dateTime;

    private ErrorType errorType;

    public enum ErrorType {
        NOT_ENOUGH_RIGHTS,
        USER_NOT_FOUND,
        ROLE_NOT_FOUND,
        EXPIRED_TOKEN,
        INCORRECT_PASSWORD,
        INVALID_TOKEN,
        REFRESH_TOKEN_WITH_NO_SESSIONS,
        USER_STATUS_BLOCKED
    }
}
