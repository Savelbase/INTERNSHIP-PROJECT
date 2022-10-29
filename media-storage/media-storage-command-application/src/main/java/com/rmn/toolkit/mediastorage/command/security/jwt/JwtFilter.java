package com.rmn.toolkit.mediastorage.command.security.jwt;

import com.rmn.toolkit.mediastorage.command.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.mediastorage.command.exception.unauthorized.InvalidTokenException;
import com.rmn.toolkit.mediastorage.command.security.SecurityUtil;
import com.rmn.toolkit.mediastorage.command.util.ResponseUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
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

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtUtil jwtUtil;
    private final SecurityUtil securityUtil;
    private final ResponseUtil responseUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String jwt = resolveToken(request);
        if (StringUtils.hasText(jwt)) {
            try {
                Authentication authentication = getAuthenticationFromJwt(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                securityUtil.checkIfUserIsBlocked(response);

                filterChain.doFilter(request, response);
            } catch (ExpiredJwtException ex) {
                log.error("Expired access token");
                GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(
                        GeneralErrorTypeErrorResponse.ErrorType.EXPIRED_TOKEN);

                securityUtil.writeErrorResponseToServletResponse(response, HttpStatus.SC_UNAUTHORIZED, errorResponse);
            } catch (Exception ex) {
                GeneralErrorTypeErrorResponse errorResponse = responseUtil.createGeneralErrorTypeErrorResponse(
                        GeneralErrorTypeErrorResponse.ErrorType.INVALID_TOKEN);

                securityUtil.writeErrorResponseToServletResponse(response, HttpStatus.SC_UNAUTHORIZED, errorResponse);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        return jwtUtil.getTokenFromHeader(header);
    }

    private Authentication getAuthenticationFromJwt(String token) {
        if (!jwtUtil.isAccessToken(token)) {
            log.error("Invalid access token");
            throw new InvalidTokenException();
        }
        return jwtTokenProvider.getAuthentication(token);
    }
}
