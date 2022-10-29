package com.rmn.toolkit.cards.query.advice;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.rmn.toolkit.cards.query.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.cards.query.dto.response.error.GeneralMessageErrorResponse;
import com.rmn.toolkit.cards.query.exception.forbidden.DifferentUserIdException;
import com.rmn.toolkit.cards.query.exception.locked.ClientStatusBlockedException;
import com.rmn.toolkit.cards.query.exception.notfound.*;
import com.rmn.toolkit.cards.query.exception.unauthorized.InvalidTokenException;
import com.rmn.toolkit.cards.query.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final int SUBSTRING_STEP = 2;
    private static final String OPERATION_TYPE_FIELD_NAME = "operationType";
    private static final String TRANSACTION_TYPE_FIELD_NAME = "transactionType";

    private final ResponseUtil responseUtil;

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<GeneralErrorTypeErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(
                GeneralErrorTypeErrorResponse.ErrorType.NOT_ENOUGH_RIGHTS);
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DifferentUserIdException.class)
    public ResponseEntity<GeneralErrorTypeErrorResponse> handleAllForbiddenExceptions(DifferentUserIdException ex) {
        GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(
                GeneralErrorTypeErrorResponse.ErrorType.NOT_ENOUGH_RIGHTS);
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<GeneralErrorTypeErrorResponse> handleInvalidTokenException(InvalidTokenException ex) {
        GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(
                GeneralErrorTypeErrorResponse.ErrorType.INVALID_TOKEN);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ClientStatusBlockedException.class)
    public ResponseEntity<GeneralErrorTypeErrorResponse> handleClientStatusBlockedException(ClientStatusBlockedException ex) {
        GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(
                GeneralErrorTypeErrorResponse.ErrorType.CLIENT_STATUS_BLOCKED);
        return new ResponseEntity<>(errorResponse, HttpStatus.LOCKED);
    }

    @ExceptionHandler({
            ClientNotFoundException.class,
            CardNotFoundException.class,
            CardRequisitesNotFoundException.class,
            CardProductNotFoundException.class,
            CardOrderNotFoundException.class,
            AgreementNotFoundException.class
    })
    public ResponseEntity<GeneralErrorTypeErrorResponse> handleAllNotFoundExceptions(ResponseStatusException ex) {
        GeneralErrorTypeErrorResponse.ErrorType errorType = null;
        if (ex instanceof ClientNotFoundException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.CLIENT_NOT_FOUND;
        } else if (ex instanceof CardNotFoundException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.CARD_NOT_FOUND;
        } else if (ex instanceof CardRequisitesNotFoundException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.CARD_REQUISITES_NOT_FOUND;
        } else if (ex instanceof CardReceiptsNotFoundException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.CARD_RECEIPTS_NOT_FOUND;
        } else if (ex instanceof CardProductNotFoundException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.CARD_PRODUCT_NOT_FOUND;
        } else if (ex instanceof CardOrderNotFoundException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.CARD_ORDER_NOT_FOUND;
        } else if (ex instanceof AgreementNotFoundException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.AGREEMENT_NOT_FOUND;
        }

        GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(errorType);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GeneralMessageErrorResponse> handleAllInternalServerExceptions(Exception ex) {
        log.error("Internal server error", ex);
        GeneralMessageErrorResponse errorResponse = responseUtil.createGeneralMessageErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        if (ex.getCause() instanceof JsonMappingException) {
            JsonMappingException exception = (JsonMappingException) ex.getCause();
            String invalidFieldName = exception.getPath().get(0).getFieldName();

            GeneralErrorTypeErrorResponse.ErrorType errorType = null;
            if (OPERATION_TYPE_FIELD_NAME.equals(invalidFieldName) ||
                    TRANSACTION_TYPE_FIELD_NAME.equals(invalidFieldName)) {
                errorType = GeneralErrorTypeErrorResponse.ErrorType.NO_SUCH_TYPE;
            }
            GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(errorType);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } else {
            GeneralMessageErrorResponse errorResponse = responseUtil.createGeneralMessageErrorResponse(ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .reduce("", (s, s2) -> String.format("%s, %s", s, s2));
        // Delete first ", " generated by reduce
        errors = errors.substring(SUBSTRING_STEP);

        GeneralMessageErrorResponse errorResponse = responseUtil.createGeneralMessageErrorResponse(errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
