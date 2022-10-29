package com.rmn.toolkit.credits.command.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmn.toolkit.credits.command.util.ClientUtil;
import com.rmn.toolkit.credits.command.util.ResponseUtil;
import com.rmn.toolkit.credits.command.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.credits.command.exception.locked.ClientStatusBlockedException;
import com.rmn.toolkit.credits.command.exception.notfound.ClientNotFoundException;
import com.rmn.toolkit.credits.command.model.Client;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class SecurityUtil {
    private final ClientUtil clientUtil;
    private final ObjectMapper mapper;
    private final ResponseUtil responseUtil;

    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public void checkIfClientIsBlocked(HttpServletResponse httpServletResponse) throws IOException {
        try {
            Client client = clientUtil.findClientById(getCurrentUserId());
            clientUtil.checkIfClientIsBlocked(client);

        } catch (ClientNotFoundException exception) {
            GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(
                    GeneralErrorTypeErrorResponse.ErrorType.CLIENT_NOT_FOUND);

            writeErrorResponseToServletResponse(httpServletResponse, HttpStatus.SC_NOT_FOUND, errorResponse);
        } catch (ClientStatusBlockedException exception) {
            GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(
                    GeneralErrorTypeErrorResponse.ErrorType.CLIENT_STATUS_BLOCKED);

            writeErrorResponseToServletResponse(httpServletResponse, HttpStatus.SC_LOCKED, errorResponse);
        }
    }

    public void writeErrorResponseToServletResponse(HttpServletResponse httpServletResponse, int httpStatus,
                                                    GeneralErrorTypeErrorResponse errorResponse) throws IOException {
        httpServletResponse.setStatus(httpStatus);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try (Writer writer = httpServletResponse.getWriter()) {
            mapper.writeValue(writer, errorResponse);
        }
    }
}
