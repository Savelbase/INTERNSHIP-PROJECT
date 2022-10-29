package com.rmn.toolkit.cards.query.dto.response.error;

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
        EXPIRED_TOKEN,
        INVALID_TOKEN,
        CARD_NOT_FOUND,
        CARD_REQUISITES_NOT_FOUND,
        CARD_RECEIPTS_NOT_FOUND,
        CARD_PRODUCT_NOT_FOUND,
        CARD_ORDER_NOT_FOUND,
        CLIENT_NOT_FOUND,
        CLIENT_STATUS_BLOCKED,
        AGREEMENT_NOT_FOUND,
        NO_SUCH_TYPE
    }
}
