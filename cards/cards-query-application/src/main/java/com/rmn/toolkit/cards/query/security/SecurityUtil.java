package com.rmn.toolkit.cards.query.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.rmn.toolkit.cards.query.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.cards.query.exception.forbidden.DifferentUserIdException;
import com.rmn.toolkit.cards.query.exception.locked.ClientStatusBlockedException;
import com.rmn.toolkit.cards.query.exception.notfound.ClientNotFoundException;
import com.rmn.toolkit.cards.query.model.Card;
import com.rmn.toolkit.cards.query.model.Client;
import com.rmn.toolkit.cards.query.security.jwt.JwtUtil;
import com.rmn.toolkit.cards.query.util.CardUtil;
import com.rmn.toolkit.cards.query.util.ClientUtil;
import com.rmn.toolkit.cards.query.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityUtil {
    private static final String ADMIN = "ADMIN";

    private final ClientUtil clientUtil;
    private final ObjectMapper mapper;
    private final ResponseUtil responseUtil;
    private final CardUtil cardUtil;
    private final JwtUtil jwtUtil;

    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public String getCurrentUserRole(String header) {
        return jwtUtil.getClaimsFromJwtToken(jwtUtil.getTokenFromHeader(header)).get("role", String.class);
    }

    public boolean isCurrentUserRoleIsAdmin(String authorizationHeader) {
        String roleName = getCurrentUserRole(authorizationHeader);
        return ADMIN.equals(roleName);
    }

    public void checkPermissionToViewCardOrder(String clientId, String authorizationHeader) {
        String clientIdFromToken = getCurrentUserId();
        String roleName = getCurrentUserRole(authorizationHeader);
        if (!Objects.equals(clientId, clientIdFromToken) && !ADMIN.equals(roleName)) {
            log.error("Client hasn't got enough rights to view card order");
            throw new AccessDeniedException("Client hasn't got enough rights to view card order");
        }
    }

    public void checkCurrentClientIdWithCardClientId(String cardId) {
        String clientId = getCurrentUserId();
        Card card = cardUtil.findCardByCardId(cardId);
        String cardClientId = card.getClient().getId();
        if (!Objects.equals(clientId, cardClientId)) {
            log.error("Client id='{}' doesn't match card clientId='{}'", clientId, cardClientId);
            throw new DifferentUserIdException();
        }
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
