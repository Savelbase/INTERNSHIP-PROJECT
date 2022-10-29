package com.rmn.toolkit.user.registration.advice;

import com.rmn.toolkit.user.registration.dto.response.error.*;
import com.rmn.toolkit.user.registration.exception.badrequest.InvalidMiddleNameException;
import com.rmn.toolkit.user.registration.exception.conflict.*;
import com.rmn.toolkit.user.registration.exception.forbidden.DifferentClientIdException;
import com.rmn.toolkit.user.registration.exception.locked.TimeCounterNotReachedException;
import com.rmn.toolkit.user.registration.exception.notfound.ClientNotFoundException;
import com.rmn.toolkit.user.registration.exception.notfound.RulesNotFoundException;
import com.rmn.toolkit.user.registration.exception.notfound.RoleNotFoundException;
import com.rmn.toolkit.user.registration.exception.notfound.VerificationCodeNotFoundException;
import com.rmn.toolkit.user.registration.exception.unauthorized.ExpiredAccessTokenException;
import com.rmn.toolkit.user.registration.exception.unauthorized.ExpiredVerificationCodeException;
import com.rmn.toolkit.user.registration.exception.unauthorized.InvalidAccessTokenException;
import com.rmn.toolkit.user.registration.exception.unauthorized.IncorrectVerificationCodeException;
import com.rmn.toolkit.user.registration.exception.locked.MaxLimitExceededException;
import com.rmn.toolkit.user.registration.util.ResponseUtil;
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
    
    private final ResponseUtil responseUtil;

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<GeneralErrorTypeErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(
                GeneralErrorTypeErrorResponse.ErrorType.NOT_ENOUGH_RIGHTS);
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DifferentClientIdException.class)
    public ResponseEntity<GeneralErrorTypeErrorResponse> handleDifferentClientIdException(DifferentClientIdException ex) {
        GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(
                GeneralErrorTypeErrorResponse.ErrorType.NOT_ENOUGH_RIGHTS);
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({
            ExpiredAccessTokenException.class,
            InvalidAccessTokenException.class,
            ExpiredVerificationCodeException.class
    })
    public ResponseEntity<GeneralErrorTypeErrorResponse> handleAllUnauthorizedExceptions(ResponseStatusException ex) {
        GeneralErrorTypeErrorResponse.ErrorType errorType = null;
        if (ex instanceof ExpiredAccessTokenException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.EXPIRED_TOKEN;
        } else if (ex instanceof InvalidAccessTokenException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.INVALID_TOKEN;
        } else if (ex instanceof ExpiredVerificationCodeException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.EXPIRED_VERIFICATION_CODE;
        }
        GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(errorType);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IncorrectVerificationCodeException.class)
    public ResponseEntity<GeneralMessageErrorResponse> handleIncorrectVerificationCodeException(IncorrectVerificationCodeException ex) {
        GeneralMessageErrorResponse errorResponse = responseUtil.createGeneralMessageErrorResponse(ex.getReason());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({
            ClientAlreadyRegisteredException.class,
            NotRFResidentException.class,
            BankClientAlreadyExistException.class,
            RequiredFieldMissingException.class,
            DuplicatePassportNumberException.class
    })
    public ResponseEntity<GeneralErrorTypeErrorResponse> handleAllConflictExceptions(ResponseStatusException ex) {
        GeneralErrorTypeErrorResponse.ErrorType errorType = null;
        if (ex instanceof ClientAlreadyRegisteredException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.CLIENT_ALREADY_IS_REGISTERED;
        } else if (ex instanceof NotRFResidentException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.NOT_RF_RESIDENT;
        } else if (ex instanceof BankClientAlreadyExistException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.BANK_CLIENT_ALREADY_EXIST;
        } else if (ex instanceof RequiredFieldMissingException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.REQUIRED_FIELD_IS_MISSING;
        } else if (ex instanceof DuplicatePassportNumberException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.DUPLICATED_PASSPORT_NUMBER;
        }
        GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(errorType);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({
            TimeCounterNotReachedException.class,
            MaxLimitExceededException.class
    })
    public ResponseEntity<GeneralMessageErrorResponse> handleAllLockedExceptions(ResponseStatusException ex) {
        GeneralMessageErrorResponse errorResponse = responseUtil.createGeneralMessageErrorResponse(ex.getReason());
        return new ResponseEntity<>(errorResponse, HttpStatus.LOCKED);
    }

    @ExceptionHandler(InvalidMiddleNameException.class)
    public ResponseEntity<GeneralMessageErrorResponse> handleInvalidClientMiddleNameException(InvalidMiddleNameException ex) {
        GeneralMessageErrorResponse errorResponse = responseUtil.createGeneralMessageErrorResponse(ex.getReason());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            ClientNotFoundException.class,
            RoleNotFoundException.class,
            VerificationCodeNotFoundException.class,
            RulesNotFoundException.class
    })
    public ResponseEntity<GeneralErrorTypeErrorResponse> handleAllNotFoundExceptions(ResponseStatusException ex) {
        GeneralErrorTypeErrorResponse.ErrorType errorType = null;
        if (ex instanceof ClientNotFoundException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.CLIENT_NOT_FOUND;
        } else if (ex instanceof RoleNotFoundException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.ROLE_NOT_FOUND;
        } else if (ex instanceof VerificationCodeNotFoundException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.VERIFICATION_CODE_NOT_FOUND;
        } else if (ex instanceof RulesNotFoundException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.RULES_NOT_FOUND;
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
        GeneralErrorTypeErrorResponse errorResponse = responseUtil
                .createGeneralErrorTypeErrorResponse(GeneralErrorTypeErrorResponse.ErrorType.RBSS_SHOULD_BE_ACCEPTED);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
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
