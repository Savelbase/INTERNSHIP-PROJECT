package com.rmn.toolkit.authorization.advice;

import com.rmn.toolkit.authorization.dto.response.error.*;
import com.rmn.toolkit.authorization.exception.conflict.RefreshTokenWithNoSessionException;
import com.rmn.toolkit.authorization.exception.locked.UserStatusBlockedException;
import com.rmn.toolkit.authorization.exception.notfound.RoleNotFoundException;
import com.rmn.toolkit.authorization.exception.notfound.UserNotFoundException;
import com.rmn.toolkit.authorization.exception.unauthorized.ExpiredRefreshTokenException;
import com.rmn.toolkit.authorization.exception.unauthorized.IncorrectPasswordException;
import com.rmn.toolkit.authorization.exception.unauthorized.IncorrectPinCodeException;
import com.rmn.toolkit.authorization.exception.locked.ApplicationIsLockedException;
import com.rmn.toolkit.authorization.exception.unauthorized.InvalidRefreshTokenException;
import com.rmn.toolkit.authorization.util.ResponseUtil;
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
    public ResponseEntity<GeneralErrorTypeErrorResponse> handleAllAccessDeniedExceptions(AccessDeniedException ex) {
        GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(
                GeneralErrorTypeErrorResponse.ErrorType.NOT_ENOUGH_RIGHTS);
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({
            ExpiredRefreshTokenException.class,
            InvalidRefreshTokenException.class,
            IncorrectPasswordException.class
    })
    public ResponseEntity<GeneralErrorTypeErrorResponse> handleUnauthorizedErrorTypeExceptions(ResponseStatusException ex) {
        GeneralErrorTypeErrorResponse.ErrorType errorType = null;
        if (ex instanceof ExpiredRefreshTokenException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.EXPIRED_TOKEN;
        } else if (ex instanceof InvalidRefreshTokenException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.INVALID_TOKEN;
        } else if (ex instanceof IncorrectPasswordException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.INCORRECT_PASSWORD;
        }
        GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(errorType);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IncorrectPinCodeException.class)
    public ResponseEntity<GeneralMessageErrorResponse> handleUnauthorizedMessageException(IncorrectPinCodeException ex) {
        GeneralMessageErrorResponse errorResponse = responseUtil.createGeneralMessageErrorResponse(ex.getReason());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ApplicationIsLockedException.class)
    public ResponseEntity<GeneralMessageErrorResponse> handleAllLockedExceptions(ApplicationIsLockedException ex) {
        GeneralMessageErrorResponse errorResponse = responseUtil.createGeneralMessageErrorResponse(ex.getReason());
        return new ResponseEntity<>(errorResponse, HttpStatus.LOCKED);
    }

    @ExceptionHandler(UserStatusBlockedException.class)
    public ResponseEntity<GeneralErrorTypeErrorResponse> handleUserStatusBlockedException(UserStatusBlockedException ex) {
        GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(
                GeneralErrorTypeErrorResponse.ErrorType.USER_STATUS_BLOCKED);
        return new ResponseEntity<>(errorResponse, HttpStatus.LOCKED);
    }

    @ExceptionHandler(RefreshTokenWithNoSessionException.class)
    public ResponseEntity<GeneralErrorTypeErrorResponse> handleConflictException(RefreshTokenWithNoSessionException ex) {
        GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(
                GeneralErrorTypeErrorResponse.ErrorType.REFRESH_TOKEN_WITH_NO_SESSIONS);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            RoleNotFoundException.class
    })
    public ResponseEntity<GeneralErrorTypeErrorResponse> handleAllNotFoundExceptions(ResponseStatusException ex) {
        GeneralErrorTypeErrorResponse.ErrorType errorType = null;
        if (ex instanceof UserNotFoundException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.USER_NOT_FOUND;
        } else if (ex instanceof RoleNotFoundException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.ROLE_NOT_FOUND;
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
