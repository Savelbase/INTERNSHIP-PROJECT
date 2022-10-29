package com.rmn.toolkit.webapigateway.advice;

import com.rmn.toolkit.webapigateway.exception.unauthorized.ExpiredTokenException;
import com.rmn.toolkit.webapigateway.exception.unauthorized.InvalidTokenException;
import com.rmn.toolkit.webapigateway.exception.unauthorized.UnauthorizedException;
import com.rmn.toolkit.webapigateway.dto.response.ErrorResponse;
import org.apache.http.HttpStatus;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class JsonErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {

    public JsonErrorWebExceptionHandler(ErrorAttributes errorAttributes,
                                        WebProperties.Resources resources,
                                        ErrorProperties errorProperties,
                                        ApplicationContext applicationContext) {
        super(errorAttributes, resources, errorProperties, applicationContext);
    }

    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Throwable throwable = super.getError(request);
        ResponseStatusException responseException;
        ErrorResponse response = ErrorResponse.builder().build();
        if (throwable instanceof UnauthorizedException) {
            responseException = (UnauthorizedException) throwable;
            initErrorResponse(response, responseException.getStatus().value(), responseException.getReason());
        } else if (throwable instanceof InvalidTokenException) {
            responseException = (InvalidTokenException) throwable;
            initErrorResponse(response, responseException.getStatus().value(), responseException.getReason());
        } else if (throwable instanceof ExpiredTokenException) {
            responseException = (ExpiredTokenException) throwable;
            initErrorResponse(response, responseException.getStatus().value(), responseException.getReason());
        } else {
            initErrorResponse(response, HttpStatus.SC_INTERNAL_SERVER_ERROR, throwable.getMessage());
        }

        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("status", response.getStatus());
        errorAttributes.put("message", response.getMessage());
        errorAttributes.put("dateTime", response.getDateTime());
        return errorAttributes;
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private void initErrorResponse(ErrorResponse response, Integer status, String message) {
        response.setStatus(status);
        response.setMessage(message);
        response.setDateTime(Instant.now());
    }
}
