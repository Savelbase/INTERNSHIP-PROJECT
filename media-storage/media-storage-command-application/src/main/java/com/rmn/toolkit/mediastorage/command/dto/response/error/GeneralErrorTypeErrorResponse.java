package com.rmn.toolkit.mediastorage.command.dto.response.error;

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
        EXPIRED_TOKEN,
        INVALID_TOKEN,
        CLIENT_STATUS_BLOCKED,
        FILE_NOT_PROVIDED,
        INVALID_FILE_EXTENSION,
        FAILED_CREATE_FOLDER,
        FILE_TOO_LARGE,
        USER_STATUS_BLOCKED
    }
}
