package com.rmn.toolkit.user.command.advice;

import com.rmn.toolkit.user.command.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.user.command.exception.forbidden.DifferentPasswordException;
import com.rmn.toolkit.user.command.exception.notfound.ClientNotFoundException;
import com.rmn.toolkit.user.command.exception.unauthorized.ExpiredAccessTokenException;
import com.rmn.toolkit.user.command.testUtil.EndpointUrlAndConstants;
import com.rmn.toolkit.user.command.util.ResponseUtil;
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

    @Test
    public void handleAccessDeniedException() {
        errorType = GeneralErrorTypeErrorResponse.ErrorType.NOT_ENOUGH_RIGHTS;
        createGeneralErrorTypeErrorResponse();

        when(responseUtil.createGeneralErrorTypeErrorResponse(errorType)).thenReturn(generalErrorTypeErrorResponse);

        ResponseEntity<GeneralErrorTypeErrorResponse> responseEntity = globalExceptionHandler
                .handleAccessDeniedException(new AccessDeniedException(EndpointUrlAndConstants.TEST_VALUE));

        assertEquals(errorType, responseEntity.getBody().getErrorType());
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    public void handleAccessDeniedExceptions() {
        errorType = GeneralErrorTypeErrorResponse.ErrorType.NOT_ENOUGH_RIGHTS;
        createGeneralErrorTypeErrorResponse();

        when(responseUtil.createGeneralErrorTypeErrorResponse(errorType)).thenReturn(generalErrorTypeErrorResponse);

        ResponseEntity<GeneralErrorTypeErrorResponse> responseEntity = globalExceptionHandler
                .handleAccessDeniedExceptions(new DifferentPasswordException());

        assertEquals(errorType, responseEntity.getBody().getErrorType());
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

    @Test
    public void handleAllExpiredExceptions() {
        errorType = GeneralErrorTypeErrorResponse.ErrorType.EXPIRED_TOKEN;
        createGeneralErrorTypeErrorResponse();

        when(responseUtil.createGeneralErrorTypeErrorResponse(errorType)).thenReturn(generalErrorTypeErrorResponse);

        ResponseEntity<GeneralErrorTypeErrorResponse> responseEntity = globalExceptionHandler
                .handleAllUnauthorizedExceptions(new ExpiredAccessTokenException());

        assertEquals(errorType, responseEntity.getBody().getErrorType());
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    public void handleAllNotFoundExceptions() {
        errorType = GeneralErrorTypeErrorResponse.ErrorType.CLIENT_NOT_FOUND;
        createGeneralErrorTypeErrorResponse();

        when(responseUtil.createGeneralErrorTypeErrorResponse(errorType)).thenReturn(generalErrorTypeErrorResponse);

        ResponseEntity<GeneralErrorTypeErrorResponse> responseEntity = globalExceptionHandler
                .handleAllNotFoundExceptions(new ClientNotFoundException(EndpointUrlAndConstants.TEST_VALUE));

        assertEquals(errorType, responseEntity.getBody().getErrorType());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    private void createGeneralErrorTypeErrorResponse() {
        generalErrorTypeErrorResponse = GeneralErrorTypeErrorResponse.builder()
                .dateTime(Instant.now())
                .errorType(errorType)
                .build();
    }
}