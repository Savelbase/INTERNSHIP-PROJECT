package com.rmn.toolkit.authorization.advice;

import com.rmn.toolkit.authorization.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.authorization.exception.notfound.UserNotFoundException;
import com.rmn.toolkit.authorization.exception.unauthorized.ExpiredRefreshTokenException;
import com.rmn.toolkit.authorization.testUtil.EndpointUrlAndConstants;
import com.rmn.toolkit.authorization.util.ResponseUtil;
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
    public void handleUnauthorizedErrorTypeExceptions() {
        errorType = GeneralErrorTypeErrorResponse.ErrorType.EXPIRED_TOKEN;
        createGeneralErrorTypeErrorResponse();

        when(responseUtil.createGeneralErrorTypeErrorResponse(errorType)).thenReturn(generalErrorTypeErrorResponse);

        ResponseEntity<GeneralErrorTypeErrorResponse> responseEntity = globalExceptionHandler
                .handleUnauthorizedErrorTypeExceptions(new ExpiredRefreshTokenException());

        assertEquals(errorType, responseEntity.getBody().getErrorType());
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    public void handleAllNotFoundExceptions() {
        errorType = GeneralErrorTypeErrorResponse.ErrorType.USER_NOT_FOUND;
        createGeneralErrorTypeErrorResponse();

        when(responseUtil.createGeneralErrorTypeErrorResponse(errorType)).thenReturn(generalErrorTypeErrorResponse);

        ResponseEntity<GeneralErrorTypeErrorResponse> responseEntity = globalExceptionHandler
                .handleAllNotFoundExceptions(new UserNotFoundException(EndpointUrlAndConstants.USER_ID));

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