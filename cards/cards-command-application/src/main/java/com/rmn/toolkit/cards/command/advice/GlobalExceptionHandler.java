package com.rmn.toolkit.cards.command.advice;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.rmn.toolkit.cards.command.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.cards.command.dto.response.error.GeneralMessageErrorResponse;
import com.rmn.toolkit.cards.command.exception.forbidden.DifferentUserIdException;
import com.rmn.toolkit.cards.command.exception.locked.ClientStatusBlockedException;
import com.rmn.toolkit.cards.command.exception.notfound.CardNotFoundException;
import com.rmn.toolkit.cards.command.exception.notfound.CardOrderNotFoundException;
import com.rmn.toolkit.cards.command.exception.notfound.CardProductNotFoundException;
import com.rmn.toolkit.cards.command.exception.notfound.ClientNotFoundException;
import com.rmn.toolkit.cards.command.exception.unauthorized.InvalidTokenException;
import com.rmn.toolkit.cards.command.util.ResponseUtil;
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
    private static final String STATUS_FIELD_NAME = "status";
    private static final String ACCEPTED_VALUE_FIELD_NAME = "acceptedValue";
    private static final String DAILY_LIMIT_SUM_FIELD_NAME = "dailyLimitSum";

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
            CardProductNotFoundException.class,
            CardOrderNotFoundException.class,
            CardNotFoundException.class
    })
    public ResponseEntity<GeneralErrorTypeErrorResponse> handleAllNotFoundExceptions(ResponseStatusException ex) {
        GeneralErrorTypeErrorResponse.ErrorType errorType = null;
        if (ex instanceof ClientNotFoundException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.CLIENT_NOT_FOUND;
        } else if (ex instanceof CardProductNotFoundException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.CARD_PRODUCT_NOT_FOUND;
        } else if (ex instanceof CardOrderNotFoundException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.CARD_ORDER_NOT_FOUND;
        } else if (ex instanceof CardNotFoundException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.CARD_NOT_FOUND;
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
            if (STATUS_FIELD_NAME.equals(invalidFieldName)) {
                errorType = GeneralErrorTypeErrorResponse.ErrorType.NO_SUCH_STATUS_TYPE;
            } else if (ACCEPTED_VALUE_FIELD_NAME.equals(invalidFieldName)) {
                errorType = GeneralErrorTypeErrorResponse.ErrorType.CONDITIONS_MUST_BE_ACCEPTED;
            } else if (DAILY_LIMIT_SUM_FIELD_NAME.equals(invalidFieldName)) {
                GeneralMessageErrorResponse errorResponse = responseUtil
                        .createGeneralMessageErrorResponse("Daily limit sum can contain only digits");
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
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
