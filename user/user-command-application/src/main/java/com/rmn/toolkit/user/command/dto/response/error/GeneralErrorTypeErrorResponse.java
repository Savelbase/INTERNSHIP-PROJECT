package com.rmn.toolkit.user.command.dto.response.error;

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
        CLIENT_NOT_FOUND,
        USER_NOT_FOUND,
        ROLE_NOT_FOUND,
        VERIFICATION_CODE_NOT_FOUND,
        PASSPORT_NOT_FOUND,
        EXPIRED_TOKEN,
        EXPIRED_VERIFICATION_CODE,
        INVALID_TOKEN,
        CLIENT_STATUS_BLOCKED,
        NO_SUCH_TYPE_NOTIFICATION,
        BANK_CLIENT_NOT_APPROVED
    }
}
