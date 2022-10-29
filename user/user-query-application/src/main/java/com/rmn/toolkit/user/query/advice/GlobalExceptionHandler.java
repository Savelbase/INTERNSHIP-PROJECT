package com.rmn.toolkit.user.query.advice;

import com.rmn.toolkit.user.query.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.user.query.dto.response.error.GeneralMessageErrorResponse;
import com.rmn.toolkit.user.query.exception.locked.ClientStatusBlockedException;
import com.rmn.toolkit.user.query.exception.notfound.ClientNotFoundException;
import com.rmn.toolkit.user.query.exception.notfound.UserNotFoundException;
import com.rmn.toolkit.user.query.exception.unauthorized.ExpiredAccessTokenException;
import com.rmn.toolkit.user.query.exception.unauthorized.InvalidAccessTokenException;
import com.rmn.toolkit.user.query.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final ResponseUtil responseUtil;

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<GeneralErrorTypeErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(
                GeneralErrorTypeErrorResponse.ErrorType.NOT_ENOUGH_RIGHTS);
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({
            ExpiredAccessTokenException.class,
            InvalidAccessTokenException.class
    })
    public ResponseEntity<GeneralErrorTypeErrorResponse> handleUnauthorizedErrorTypeException(ResponseStatusException ex) {
        GeneralErrorTypeErrorResponse.ErrorType errorType = null;
        if (ex instanceof ExpiredAccessTokenException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.EXPIRED_TOKEN;
        } else if (ex instanceof InvalidAccessTokenException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.INVALID_TOKEN;
        }
        GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(errorType);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ClientStatusBlockedException.class)
    public ResponseEntity<GeneralErrorTypeErrorResponse> handleClientStatusBlockedException(ClientStatusBlockedException ex) {
        GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(
                GeneralErrorTypeErrorResponse.ErrorType.CLIENT_STATUS_BLOCKED);
        return new ResponseEntity<>(errorResponse, HttpStatus.LOCKED);
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            ClientNotFoundException.class
    })
    public ResponseEntity<GeneralErrorTypeErrorResponse> handleAllNotFoundExceptions(ResponseStatusException ex) {
        GeneralErrorTypeErrorResponse.ErrorType errorType = null;
        if (ex instanceof UserNotFoundException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.USER_NOT_FOUND;
        } else if (ex instanceof ClientNotFoundException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.CLIENT_NOT_FOUND;
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
