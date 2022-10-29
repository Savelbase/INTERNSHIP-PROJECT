package com.rmn.toolkit.mediastorage.command.advice;

import com.rmn.toolkit.mediastorage.command.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.mediastorage.command.dto.response.error.GeneralMessageErrorResponse;
import com.rmn.toolkit.mediastorage.command.exception.badrequest.FileNotProvidedException;
import com.rmn.toolkit.mediastorage.command.exception.conflict.FolderCreationException;
import com.rmn.toolkit.mediastorage.command.exception.locked.UserStatusBlockedException;
import com.rmn.toolkit.mediastorage.command.exception.notfound.UserNotFoundException;
import com.rmn.toolkit.mediastorage.command.exception.unauthorized.InvalidTokenException;
import com.rmn.toolkit.mediastorage.command.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.mime.MimeTypeException;
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
import org.springframework.web.multipart.MultipartException;
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

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<GeneralErrorTypeErrorResponse> handleInvalidTokenException(InvalidTokenException ex) {
        GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(
                GeneralErrorTypeErrorResponse.ErrorType.INVALID_TOKEN);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserStatusBlockedException.class)
    public ResponseEntity<GeneralErrorTypeErrorResponse> handleUserStatusBlockedException(UserStatusBlockedException ex) {
        GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(
                GeneralErrorTypeErrorResponse.ErrorType.USER_STATUS_BLOCKED);
        return new ResponseEntity<>(errorResponse, HttpStatus.LOCKED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<GeneralErrorTypeErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(
                GeneralErrorTypeErrorResponse.ErrorType.USER_NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            FileNotProvidedException.class,
            MimeTypeException.class
    })
    public ResponseEntity<GeneralErrorTypeErrorResponse> handleBadRequestExceptions(Exception ex) {
        GeneralErrorTypeErrorResponse.ErrorType errorType = null;
        if (ex instanceof FileNotProvidedException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.FILE_NOT_PROVIDED;
        } else if (ex instanceof MimeTypeException) {
            errorType = GeneralErrorTypeErrorResponse.ErrorType.INVALID_FILE_EXTENSION;
        }
        GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(errorType);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<GeneralErrorTypeErrorResponse> handleFileSizeExceededException(MultipartException ex) {
        GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(
                GeneralErrorTypeErrorResponse.ErrorType.FILE_TOO_LARGE);
        return new ResponseEntity<>(errorResponse, HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @ExceptionHandler(FolderCreationException.class)
    public ResponseEntity<GeneralErrorTypeErrorResponse> handleFolderCreationExceptionException(FolderCreationException ex) {
        GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(
                GeneralErrorTypeErrorResponse.ErrorType.FAILED_CREATE_FOLDER);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
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
