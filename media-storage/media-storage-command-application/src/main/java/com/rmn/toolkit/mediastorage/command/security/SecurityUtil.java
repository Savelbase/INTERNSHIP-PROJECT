package com.rmn.toolkit.mediastorage.command.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmn.toolkit.mediastorage.command.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.mediastorage.command.exception.notfound.UserNotFoundException;
import com.rmn.toolkit.mediastorage.command.exception.locked.UserStatusBlockedException;
import com.rmn.toolkit.mediastorage.command.model.User;
import com.rmn.toolkit.mediastorage.command.util.ResponseUtil;
import com.rmn.toolkit.mediastorage.command.util.UserUtil;
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
    private final UserUtil userUtil;
    private final ObjectMapper mapper;
    private final ResponseUtil responseUtil;

    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public void checkIfUserIsBlocked(HttpServletResponse httpServletResponse) throws IOException {
        try {
            User user = userUtil.findUserById(getCurrentUserId());
            userUtil.checkIfUserIsBlocked(user);

        } catch (UserNotFoundException exception) {
            GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(
                    GeneralErrorTypeErrorResponse.ErrorType.USER_NOT_FOUND);

            writeErrorResponseToServletResponse(httpServletResponse, HttpStatus.SC_NOT_FOUND, errorResponse);
        } catch (UserStatusBlockedException exception) {
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
