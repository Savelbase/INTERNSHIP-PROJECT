package com.rmn.toolkit.mediastorage.query.advice;

import com.rmn.toolkit.mediastorage.query.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.mediastorage.query.dto.response.error.GeneralMessageErrorResponse;
import com.rmn.toolkit.mediastorage.query.exception.locked.UserStatusBlockedException;
import com.rmn.toolkit.mediastorage.query.exception.notfound.FileNotFoundException;
import com.rmn.toolkit.mediastorage.query.exception.unauthorized.InvalidTokenException;
import com.rmn.toolkit.mediastorage.query.testUtil.EndpointUrlAndConstants;
import com.rmn.toolkit.mediastorage.query.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {
        GlobalExceptionHandler.class
})
@MockBeans(value = {
        @MockBean(ResponseUtil.class)
})
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GlobalExceptionHandlerForGeneralErrorTypeErrorResponseTest {
    private final GlobalExceptionHandler globalExceptionHandler;
    private final ResponseUtil responseUtil;
    private GeneralErrorTypeErrorResponse.ErrorType errorType;
    private GeneralErrorTypeErrorResponse generalErrorTypeErrorResponse;
    private GeneralMessageErrorResponse generalMessageErrorResponse;

    @Test
    public void handleAllAccessDeniedExceptions() {
        errorType = GeneralErrorTypeErrorResponse.ErrorType.NOT_ENOUGH_RIGHTS;
        createGeneralErrorTypeErrorResponse();

        when(responseUtil.createGeneralErrorTypeErrorResponse(errorType)).thenReturn(generalErrorTypeErrorResponse);

        ResponseEntity<GeneralErrorTypeErrorResponse> responseEntity = globalExceptionHandler
                .handleAllAccessDeniedExceptions(new AccessDeniedException(EndpointUrlAndConstants.TEST_VALUE));

        assertEquals(errorType, responseEntity.getBody().getErrorType());
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    public void handleInvalidTokenException() {
        errorType = GeneralErrorTypeErrorResponse.ErrorType.INVALID_TOKEN;
        createGeneralErrorTypeErrorResponse();

        when(responseUtil.createGeneralErrorTypeErrorResponse(errorType)).thenReturn(generalErrorTypeErrorResponse);

        ResponseEntity<GeneralErrorTypeErrorResponse> responseEntity = globalExceptionHandler
                .handleInvalidTokenException(new InvalidTokenException());

        assertEquals(errorType, responseEntity.getBody().getErrorType());
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    public void handleUserStatusBlockedException() {
        errorType = GeneralErrorTypeErrorResponse.ErrorType.USER_STATUS_BLOCKED;
        createGeneralErrorTypeErrorResponse();

        when(responseUtil.createGeneralErrorTypeErrorResponse(errorType)).thenReturn(generalErrorTypeErrorResponse);

        ResponseEntity<GeneralErrorTypeErrorResponse> responseEntity = globalExceptionHandler
                .handleUserStatusBlockedException(new UserStatusBlockedException());

        assertEquals(errorType, responseEntity.getBody().getErrorType());
        assertEquals(HttpStatus.LOCKED, responseEntity.getStatusCode());
    }

    @Test
    public void handleAllNotFoundExceptions() {
        errorType = GeneralErrorTypeErrorResponse.ErrorType.FILE_NOT_FOUND;
        createGeneralErrorTypeErrorResponse();

        when(responseUtil.createGeneralErrorTypeErrorResponse(errorType)).thenReturn(generalErrorTypeErrorResponse);

        ResponseEntity<GeneralErrorTypeErrorResponse> responseEntity = globalExceptionHandler
                .handleAllNotFoundExceptions(new FileNotFoundException(EndpointUrlAndConstants.TEST_VALUE));

        assertEquals(errorType, responseEntity.getBody().getErrorType());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void handleAllInternalServerExceptions() {
        createGeneralMessageErrorResponse();
        when(responseUtil.createGeneralMessageErrorResponse(anyString())).thenReturn(generalMessageErrorResponse);

        ResponseEntity<GeneralMessageErrorResponse> responseEntity = globalExceptionHandler
                .handleAllInternalServerExceptions(new Exception());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    private void createGeneralErrorTypeErrorResponse() {
        generalErrorTypeErrorResponse = GeneralErrorTypeErrorResponse.builder()
                .dateTime(Instant.now())
                .errorType(errorType)
                .build();
    }

    private void createGeneralMessageErrorResponse() {
        generalMessageErrorResponse = GeneralMessageErrorResponse.builder()
                .dateTime(Instant.now())
                .message(EndpointUrlAndConstants.TEST_VALUE)
                .build();
    }
}