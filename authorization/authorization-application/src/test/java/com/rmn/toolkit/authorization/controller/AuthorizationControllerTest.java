package com.rmn.toolkit.authorization.controller;

import com.rmn.toolkit.authorization.AuthorizationApplication;
import com.rmn.toolkit.authorization.dto.request.PhoneAndPasswordDto;
import com.rmn.toolkit.authorization.dto.request.PhoneAndPinCodeDto;
import com.rmn.toolkit.authorization.dto.request.RefreshTokenDto;
import com.rmn.toolkit.authorization.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.authorization.dto.response.error.GeneralMessageErrorResponse;
import com.rmn.toolkit.authorization.dto.response.success.SuccessResponse;
import com.rmn.toolkit.authorization.dto.response.success.TokensResponse;
import com.rmn.toolkit.authorization.model.RefreshToken;
import com.rmn.toolkit.authorization.repository.RefreshTokenRepository;
import com.rmn.toolkit.authorization.security.jwt.JwtUtil;
import com.rmn.toolkit.authorization.testUtil.EndpointUrlAndConstants;
import com.rmn.toolkit.authorization.testUtil.RequestDtoBuilder;
import com.rmn.toolkit.authorization.testUtil.token.TokenBuilder;
import com.rmn.toolkit.authorization.testUtil.token.TokenType;
import com.rmn.toolkit.authorization.util.RefreshTokenUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("integration-test")
@SpringBootTest(
        classes = AuthorizationApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthorizationControllerTest {
    private static final int MAX_LIMIT = 3;

    @LocalServerPort
    private Integer port;
    @Value("${authentication.token.type}")
    private String tokenType;
    @Value("${authentication.token.accessTokenExpirationSec}")
    private Integer accessTokenExpirationSec;
    @Value("${authentication.token.refreshTokenExpirationSec}")
    private Integer refreshTokenExpirationSec;
    @Autowired
    private TestRestTemplate testRestTemplate;
    private final HttpHeaders headers;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RefreshTokenUtil refreshTokenUtil;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private TokenBuilder tokenBuilder;
    @Autowired
    private RequestDtoBuilder requestDtoBuilder;

    public AuthorizationControllerTest() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @Order(1)
    public void loginWithPhoneAndPasswordWhenValidRequestBodyAppliedShouldReturnOkStatusCode() {
        PhoneAndPasswordDto phoneAndPasswordDto = requestDtoBuilder
                .createPhoneAndPasswordDto(EndpointUrlAndConstants.MOBILE_PHONE, EndpointUrlAndConstants.PASSWORD);

        HttpEntity<PhoneAndPasswordDto> entity = new HttpEntity<>(phoneAndPasswordDto, headers);
        ResponseEntity<TokensResponse> response = testRestTemplate
                .postForEntity(createURI(EndpointUrlAndConstants.LOGIN_WITH_PHONE_AND_PASSWORD), entity, TokensResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        TokensResponse accessTokenResponse = response.getBody();
        assertNotNull(accessTokenResponse);
        assertEquals(accessTokenResponse.getTokenType(), tokenType);
        assertEquals(accessTokenResponse.getExpirationTime(), accessTokenExpirationSec);

        validateTokens(accessTokenResponse);
    }

    @Test
    @Order(2)
    public void loginWithPhoneAndPasswordWhenIncorrectPasswordAppliedShouldReturnUnauthorizedStatusCode() {
        PhoneAndPasswordDto phoneAndPasswordDto = requestDtoBuilder
                .createPhoneAndPasswordDto(EndpointUrlAndConstants.MOBILE_PHONE, EndpointUrlAndConstants.INCORRECT_PASSWORD);

        HttpEntity<PhoneAndPasswordDto> entity = new HttpEntity<>(phoneAndPasswordDto, headers);
        ResponseEntity<GeneralErrorTypeErrorResponse> response = testRestTemplate
                .postForEntity(createURI(EndpointUrlAndConstants.LOGIN_WITH_PHONE_AND_PASSWORD), entity,
                        GeneralErrorTypeErrorResponse.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

        GeneralErrorTypeErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(GeneralErrorTypeErrorResponse.ErrorType.INCORRECT_PASSWORD, errorResponse.getErrorType());
    }

    @Test
    @Order(3)
    public void loginWithPhoneAndPinCodeWhenValidRequestBodyAppliedShouldReturnOkStatusCode() {
        PhoneAndPinCodeDto phoneAndPinCodeDto = requestDtoBuilder
                .createPhoneAndPinCodeDto(EndpointUrlAndConstants.MOBILE_PHONE, EndpointUrlAndConstants.PIN_CODE);

        HttpEntity<PhoneAndPinCodeDto> entity = new HttpEntity<>(phoneAndPinCodeDto, headers);
        ResponseEntity<TokensResponse> response = testRestTemplate
                .postForEntity(createURI(EndpointUrlAndConstants.LOGIN_WITH_PHONE_AND_PIN), entity, TokensResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        TokensResponse accessTokenResponse = response.getBody();
        assertNotNull(accessTokenResponse);
        assertEquals(accessTokenResponse.getTokenType(), tokenType);
        assertEquals(accessTokenResponse.getExpirationTime(), accessTokenExpirationSec);

        validateTokens(accessTokenResponse);
    }

    @Test
    @Order(4)
    public void loginWithPhoneAndPinCodeWhenInvalidPinCodeAppliedShouldReturnBadRequestStatusCode() {
        PhoneAndPinCodeDto phoneAndPinCodeDto = requestDtoBuilder
                .createPhoneAndPinCodeDto(EndpointUrlAndConstants.MOBILE_PHONE, EndpointUrlAndConstants.TEST_VALUE);

        HttpEntity<PhoneAndPinCodeDto> entity = new HttpEntity<>(phoneAndPinCodeDto, headers);
        ResponseEntity<GeneralMessageErrorResponse> response = testRestTemplate
                .postForEntity(createURI(EndpointUrlAndConstants.LOGIN_WITH_PHONE_AND_PIN), entity,
                        GeneralMessageErrorResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        GeneralMessageErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("PIN code can contain only 6 digits", errorResponse.getMessage());
    }

    @Test
    @Order(5)
    public void refreshTokenWhenValidRequestBodyAppliedShouldReturnOkStatusCode() {
        String refreshTokenValue = tokenBuilder.generateTokenWithUserId(TokenType.REFRESH, EndpointUrlAndConstants.USER_ID);
        RefreshToken refreshToken = createRefreshToken(refreshTokenValue);
        refreshTokenRepository.save(refreshToken);

        RefreshTokenDto refreshTokenDto = requestDtoBuilder.createRefreshTokenDto(refreshTokenValue);

        HttpEntity<RefreshTokenDto> entity = new HttpEntity<>(refreshTokenDto, headers);
        ResponseEntity<TokensResponse> response = testRestTemplate
                .postForEntity(createURI(EndpointUrlAndConstants.REFRESH_TOKEN), entity, TokensResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        TokensResponse accessTokenResponse = response.getBody();
        assertNotNull(accessTokenResponse);
        assertEquals(accessTokenResponse.getTokenType(), tokenType);
        assertEquals(accessTokenResponse.getExpirationTime(), accessTokenExpirationSec);

        validateTokens(accessTokenResponse);
    }

    @Test
    @Order(6)
    public void refreshTokenWhenAccessTokenAppliedShouldReturnUnauthorizedStatusCode() {
        String accessTokenValue = tokenBuilder.generateTokenWithUserId(TokenType.ACCESS, EndpointUrlAndConstants.USER_ID);
        RefreshTokenDto refreshTokenDto = requestDtoBuilder.createRefreshTokenDto(accessTokenValue);

        HttpEntity<RefreshTokenDto> entity = new HttpEntity<>(refreshTokenDto, headers);
        ResponseEntity<GeneralErrorTypeErrorResponse> response = testRestTemplate
                .postForEntity(createURI(EndpointUrlAndConstants.REFRESH_TOKEN), entity, GeneralErrorTypeErrorResponse.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

        GeneralErrorTypeErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(GeneralErrorTypeErrorResponse.ErrorType.INVALID_TOKEN, errorResponse.getErrorType());
    }

    @Test
    @Order(7)
    public void logoutWhenValidRequestBodyAppliedShouldReturnOkStatusCode() {
        String refreshTokenValue = tokenBuilder.generateTokenWithUserId(TokenType.REFRESH, EndpointUrlAndConstants.USER_ID);
        RefreshToken refreshToken = createRefreshToken(refreshTokenValue);
        refreshTokenRepository.save(refreshToken);

        RefreshTokenDto refreshTokenDto = requestDtoBuilder.createRefreshTokenDto(refreshTokenValue);

        HttpEntity<RefreshTokenDto> entity = new HttpEntity<>(refreshTokenDto, headers);
        ResponseEntity<SuccessResponse> response = testRestTemplate
                .postForEntity(createURI(EndpointUrlAndConstants.LOGOUT), entity, SuccessResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        SuccessResponse successResponse = response.getBody();
        assertNotNull(successResponse);
        assertEquals("Operation completed successfully", successResponse.getMessage());
    }

    @Test
    @Order(8)
    public void logoutWhenRefreshTokenWithNoSessionAppliedShouldReturnConflictStatusCode() {
        String refreshTokenValue = tokenBuilder.generateTokenWithUserId(TokenType.REFRESH, EndpointUrlAndConstants.USER_ID);
        RefreshTokenDto refreshTokenDto = requestDtoBuilder.createRefreshTokenDto(refreshTokenValue);

        HttpEntity<RefreshTokenDto> entity = new HttpEntity<>(refreshTokenDto, headers);
        ResponseEntity<GeneralErrorTypeErrorResponse> response = testRestTemplate
                .postForEntity(createURI(EndpointUrlAndConstants.LOGOUT), entity, GeneralErrorTypeErrorResponse.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

        GeneralErrorTypeErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(GeneralErrorTypeErrorResponse.ErrorType.REFRESH_TOKEN_WITH_NO_SESSIONS, errorResponse.getErrorType());
    }

    @Test
    @Order(9)
    public void logoutWhenExpiredRefreshTokenAppliedShouldReturnUnauthorizedStatusCode() {
        String refreshTokenValue = tokenBuilder.generateExpiredTokenWithUserId(TokenType.REFRESH, EndpointUrlAndConstants.USER_ID);
        RefreshTokenDto refreshTokenDto = requestDtoBuilder.createRefreshTokenDto(refreshTokenValue);

        HttpEntity<RefreshTokenDto> entity = new HttpEntity<>(refreshTokenDto, headers);
        ResponseEntity<GeneralErrorTypeErrorResponse> response = testRestTemplate
                .postForEntity(createURI(EndpointUrlAndConstants.LOGOUT), entity, GeneralErrorTypeErrorResponse.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

        GeneralErrorTypeErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(GeneralErrorTypeErrorResponse.ErrorType.EXPIRED_TOKEN, errorResponse.getErrorType());
    }

    @Test
    @Order(10)
    public void loginWithPhoneAndPinCodeWhenMaxLimitAchievedShouldReturnLockedRequestStatusCode() {
        PhoneAndPinCodeDto phoneAndPinCodeDto = requestDtoBuilder
                .createPhoneAndPinCodeDto(EndpointUrlAndConstants.MOBILE_PHONE, EndpointUrlAndConstants.INCORRECT_PIN_CODE);

        HttpEntity<PhoneAndPinCodeDto> entity = new HttpEntity<>(phoneAndPinCodeDto, headers);
        ResponseEntity<GeneralMessageErrorResponse> response = null;
        for (int i = 0; i < MAX_LIMIT; i++) {
            response = testRestTemplate.postForEntity(createURI(EndpointUrlAndConstants.LOGIN_WITH_PHONE_AND_PIN),
                    entity, GeneralMessageErrorResponse.class);
        }

        assertEquals(HttpStatus.LOCKED, response.getStatusCode());

        GeneralMessageErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Application is locked. You can unlock it using 'Reset PIN code' button", errorResponse.getMessage());
    }

    private String createURI(String url) {
        return String.format("http://localhost:%s/%s", port, url);
    }

    private void validateTokens(TokensResponse tokens) {
        String accessToken = tokens.getAccessToken();
        String refreshToken = tokens.getRefreshToken();

        Claims accessTokenClaims = jwtUtil.getClaimsFromJwt(accessToken);
        assertTrue(accessTokenClaims.containsKey("authorities"));
        validateTokenClaims(accessTokenClaims);


        Claims refreshTokenClaims = jwtUtil.getClaimsFromJwt(refreshToken);
        validateTokenClaims(refreshTokenClaims);
    }

    private void validateTokenClaims(Claims claims) {
        assertNotNull(claims.getSubject());
        assertEquals(EndpointUrlAndConstants.USER_ID, claims.getSubject());

        assertNotNull(claims.getIssuer());
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());

        assertTrue(claims.getIssuedAt().compareTo(new Date()) < 0);
        assertTrue(claims.getExpiration().compareTo(new Date()) > 0);
    }

    private RefreshToken createRefreshToken(String refreshToken) {
        String refreshTokenHash = JwtUtil.getTokenHash(refreshToken);

        Instant currentTime = Instant.now();
        Instant refreshTokenExpirationDate = currentTime.plus(refreshTokenExpirationSec, ChronoUnit.SECONDS);

        return refreshTokenUtil.createRefreshToken(refreshTokenHash, refreshTokenExpirationDate, EndpointUrlAndConstants.USER_ID);
    }
}
