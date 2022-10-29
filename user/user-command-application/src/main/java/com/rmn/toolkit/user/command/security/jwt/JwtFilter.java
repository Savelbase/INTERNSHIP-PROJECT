package com.rmn.toolkit.user.command.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmn.toolkit.user.command.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.user.command.exception.unauthorized.InvalidAccessTokenException;
import com.rmn.toolkit.user.command.util.ResponseUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtUtil jwtUtil;
    private final ResponseUtil responseUtil;
    private final ObjectMapper mapper;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest,
                                    @NonNull HttpServletResponse httpServletResponse,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String jwt = resolveToken(httpServletRequest);
        if (StringUtils.hasText(jwt)) {
            try {
                Authentication authentication = getAuthenticationFromJwt(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                filterChain.doFilter(httpServletRequest, httpServletResponse);
            } catch (ExpiredJwtException ex) {
                log.error("Expired access token");
                GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(
                        GeneralErrorTypeErrorResponse.ErrorType.EXPIRED_TOKEN);

                writeErrorResponseToServletResponse(httpServletResponse, HttpStatus.SC_UNAUTHORIZED, errorResponse);
            } catch (Exception ex) {
                GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(
                        GeneralErrorTypeErrorResponse.ErrorType.INVALID_TOKEN);

                writeErrorResponseToServletResponse(httpServletResponse, HttpStatus.SC_UNAUTHORIZED, errorResponse);
            }
        } else {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        return jwtUtil.getTokenFromHeader(header);
    }

    private Authentication getAuthenticationFromJwt(String token) {
        if (!jwtUtil.isAccessToken(token)) {
            log.error("Invalid access token");
            throw new InvalidAccessTokenException();
        }
        return jwtTokenProvider.getAuthentication(token);
    }

    private void writeErrorResponseToServletResponse(HttpServletResponse httpServletResponse, int httpStatus,
                                                    GeneralErrorTypeErrorResponse errorResponse) throws IOException {
        httpServletResponse.setStatus(httpStatus);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try (Writer writer = httpServletResponse.getWriter()) {
            mapper.writeValue(writer, errorResponse);
        }
    }
}
