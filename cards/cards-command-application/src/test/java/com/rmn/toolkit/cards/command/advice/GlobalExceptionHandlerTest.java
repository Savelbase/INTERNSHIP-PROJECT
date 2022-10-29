package com.rmn.toolkit.cards.command.advice;

import com.rmn.toolkit.cards.command.exception.notfound.CardOrderNotFoundException;
import com.rmn.toolkit.cards.command.testUtil.EndpointUrlAndConstants;
import com.rmn.toolkit.cards.command.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.cards.command.dto.response.error.GeneralMessageErrorResponse;
import com.rmn.toolkit.cards.command.exception.locked.ClientStatusBlockedException;
import com.rmn.toolkit.cards.command.exception.unauthorized.InvalidTokenException;
import com.rmn.toolkit.cards.command.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
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
public class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler globalExceptionHandler;
    private final ResponseUtil responseUtil;
    private GeneralErrorTypeErrorResponse.ErrorType errorType;
    private GeneralErrorTypeErrorResponse generalErrorTypeErrorResponse;
    private static GeneralMessageErrorResponse generalMessageErrorResponse;

    @BeforeAll
    public static void createGeneralMessageErrorResponse() {
        generalMessageErrorResponse = GeneralMessageErrorResponse.builder()
                .dateTime(Instant.now())
                .message(EndpointUrlAndConstants.TEST_VALUE)
                .build();
    }

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
    public void handleClientStatusBlockedException() {
        errorType = GeneralErrorTypeErrorResponse.ErrorType.CLIENT_STATUS_BLOCKED;
        createGeneralErrorTypeErrorResponse();

        when(responseUtil.createGeneralErrorTypeErrorResponse(errorType)).thenReturn(generalErrorTypeErrorResponse);

        ResponseEntity<GeneralErrorTypeErrorResponse> responseEntity = globalExceptionHandler
                .handleClientStatusBlockedException(new ClientStatusBlockedException());

        assertEquals(errorType, responseEntity.getBody().getErrorType());
        assertEquals(HttpStatus.LOCKED, responseEntity.getStatusCode());
    }

    @Test
    public void handleAllNotFoundExceptions() {
        errorType = GeneralErrorTypeErrorResponse.ErrorType.CARD_ORDER_NOT_FOUND;
        createGeneralErrorTypeErrorResponse();

        when(responseUtil.createGeneralErrorTypeErrorResponse(errorType)).thenReturn(generalErrorTypeErrorResponse);

        ResponseEntity<GeneralErrorTypeErrorResponse> responseEntity = globalExceptionHandler
                .handleAllNotFoundExceptions(new CardOrderNotFoundException(EndpointUrlAndConstants.TEST_VALUE));

        assertEquals(errorType, responseEntity.getBody().getErrorType());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void handleAllInternalServerExceptions() {
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
}