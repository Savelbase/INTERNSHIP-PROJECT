package com.rmn.toolkit.user.command.advice;

import com.rmn.toolkit.user.command.dto.response.error.GeneralMessageErrorResponse;
import com.rmn.toolkit.user.command.exception.locked.TimeCounterNotReachedException;
import com.rmn.toolkit.user.command.exception.unauthorized.IncorrectVerificationCodeException;
import com.rmn.toolkit.user.command.testUtil.EndpointUrlAndConstants;
import com.rmn.toolkit.user.command.util.ResponseUtil;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

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
    public void handleAllInvalidEntityExceptions() {
        when(responseUtil.createGeneralMessageErrorResponse(anyString())).thenReturn(generalMessageErrorResponse);

        ResponseEntity<GeneralMessageErrorResponse> responseEntity = globalExceptionHandler
                .handleIncorrectVerificationCodeException(
                        new IncorrectVerificationCodeException(EndpointUrlAndConstants.TEST_INT_VALUE));

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    public void handleTimeCounterNotReachedException() {
        when(responseUtil.createGeneralMessageErrorResponse(anyString())).thenReturn(generalMessageErrorResponse);

        ResponseEntity<GeneralMessageErrorResponse> responseEntity = globalExceptionHandler
                .handleAllLockedExceptions(new TimeCounterNotReachedException());

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