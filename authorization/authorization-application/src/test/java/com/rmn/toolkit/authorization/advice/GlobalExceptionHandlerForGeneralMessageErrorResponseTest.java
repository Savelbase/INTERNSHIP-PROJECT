package com.rmn.toolkit.authorization.advice;

import com.rmn.toolkit.authorization.dto.response.error.GeneralMessageErrorResponse;
import com.rmn.toolkit.authorization.exception.locked.ApplicationIsLockedException;
import com.rmn.toolkit.authorization.exception.unauthorized.IncorrectPinCodeException;
import com.rmn.toolkit.authorization.testUtil.EndpointUrlAndConstants;
import com.rmn.toolkit.authorization.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {
        GlobalExceptionHandler.class
})
@MockBeans(value = {
        @MockBean(ResponseUtil.class)
})
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GlobalExceptionHandlerForGeneralMessageErrorResponseTest {
    private final GlobalExceptionHandler globalExceptionHandler;
    private final ResponseUtil responseUtil;
    private static GeneralMessageErrorResponse generalMessageErrorResponse;

    @BeforeAll
    public static void createGeneralMessageErrorResponse() {
        generalMessageErrorResponse = GeneralMessageErrorResponse.builder()
                .dateTime(Instant.now())
                .message(EndpointUrlAndConstants.TEST_VALUE)
                .build();
    }

    @Test
    public void handleUnauthorizedMessageException() {
        when(responseUtil.createGeneralMessageErrorResponse(anyString())).thenReturn(generalMessageErrorResponse);

        ResponseEntity<GeneralMessageErrorResponse> responseEntity = globalExceptionHandler
                .handleUnauthorizedMessageException(new IncorrectPinCodeException(EndpointUrlAndConstants.TEST_INT_VALUE));

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    public void handleAllLockedExceptions() {
        when(responseUtil.createGeneralMessageErrorResponse(anyString())).thenReturn(generalMessageErrorResponse);

        ResponseEntity<GeneralMessageErrorResponse> responseEntity = globalExceptionHandler
                .handleAllLockedExceptions(new ApplicationIsLockedException());

        assertEquals(HttpStatus.LOCKED, responseEntity.getStatusCode());
    }

    @Test
    public void handleAllInternalServerExceptions() {
        when(responseUtil.createGeneralMessageErrorResponse(anyString())).thenReturn(generalMessageErrorResponse);

        ResponseEntity<GeneralMessageErrorResponse> responseEntity = globalExceptionHandler
                .handleAllInternalServerExceptions(new Exception());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}