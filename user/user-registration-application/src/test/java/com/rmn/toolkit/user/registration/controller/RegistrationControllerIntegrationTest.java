package com.rmn.toolkit.user.registration.controller;

import com.rmn.toolkit.user.registration.UserRegistrationApplication;
import com.rmn.toolkit.user.registration.dto.request.*;
import com.rmn.toolkit.user.registration.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.user.registration.dto.response.error.GeneralMessageErrorResponse;
import com.rmn.toolkit.user.registration.dto.response.success.AccessTokenResponse;
import com.rmn.toolkit.user.registration.dto.response.success.ClientIdResponse;
import com.rmn.toolkit.user.registration.dto.response.success.SuccessResponse;
import com.rmn.toolkit.user.registration.dto.response.success.VerificationCodeResponse;
import com.rmn.toolkit.user.registration.security.jwt.JwtUtil;
import com.rmn.toolkit.user.registration.testUtil.EndpointUrlAndConstants;
import com.rmn.toolkit.user.registration.testUtil.ModelUtil;
import com.rmn.toolkit.user.registration.testUtil.RequestDtoBuilder;
import com.rmn.toolkit.user.registration.testUtil.TokenBuilder;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("integration-test")
@SpringBootTest(
        classes = UserRegistrationApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegistrationControllerIntegrationTest {
    @LocalServerPort
    private Integer port;
    @Value("${authentication.token.type}")
    private String tokenType;
    @Value("${authentication.token.accessTokenExpirationSec}")
    private Integer accessTokenExpirationSec;
    @Value("${authentication.verificationCode.expirationSec}")
    private Integer verificationCodeExpirationSec;
    @Autowired
    private TestRestTemplate testRestTemplate;
    private RestTemplate restTemplate;
    private final HttpHeaders headers;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private TokenBuilder tokenBuilder;
    @Autowired
    private RequestDtoBuilder requestDtoBuilder;
    @Autowired
    private ModelUtil modelUtil;

    public RegistrationControllerIntegrationTest() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @BeforeEach
    public void setUp() {
        restTemplate = testRestTemplate.getRestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Test
    @Order(1)
    public void checkMobilePhoneAndGenerateTokenWhenValidRequestBodyAppliedShouldReturnCreatedStatusCode() {
        MobilePhoneDto mobilePhoneDto = requestDtoBuilder.createMobilePhoneDto(EndpointUrlAndConstants.CLIENT_MOBILE_PHONE);

        HttpEntity<MobilePhoneDto> entity = new HttpEntity<>(mobilePhoneDto, headers);
        ResponseEntity<AccessTokenResponse> response = testRestTemplate
                .postForEntity(createURI(EndpointUrlAndConstants.CHECK_MOBILE_PHONE_AND_GENERATE_TOKEN), entity,
                        AccessTokenResponse.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        AccessTokenResponse accessTokenResponse = response.getBody();
        assertNotNull(accessTokenResponse);
        assertEquals(accessTokenResponse.getTokenType(), tokenType);
        assertEquals(accessTokenResponse.getAccessTokenExpirationTime(), accessTokenExpirationSec);

        validateAccessToken(accessTokenResponse.getAccessToken());
    }

    @Test
    @Order(2)
    public void checkMobilePhoneAndGenerateTokenWhenRegisteredClientAppliedShouldReturnConflictStatusCode() {
        MobilePhoneDto mobilePhoneDto = requestDtoBuilder.createMobilePhoneDto(EndpointUrlAndConstants.ADMIN_MOBILE_PHONE);

        HttpEntity<MobilePhoneDto> entity = new HttpEntity<>(mobilePhoneDto, headers);
        ResponseEntity<GeneralErrorTypeErrorResponse> response = testRestTemplate
                .postForEntity(createURI(EndpointUrlAndConstants.CHECK_MOBILE_PHONE_AND_GENERATE_TOKEN), entity,
                        GeneralErrorTypeErrorResponse.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

        GeneralErrorTypeErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(GeneralErrorTypeErrorResponse.ErrorType.CLIENT_ALREADY_IS_REGISTERED, errorResponse.getErrorType());
    }

    @Test
    @Order(3)
    public void createVerificationCodeWhenValidHeadersAppliedShouldReturnCreatedStatusCode() {
        setAccessTokenToHeader(false);

        HttpEntity<String> entity = new HttpEntity<>("params", headers);
        ResponseEntity<VerificationCodeResponse> response = testRestTemplate
                .postForEntity(createURI(EndpointUrlAndConstants.CODE_GENERATION), entity,
                        VerificationCodeResponse.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        VerificationCodeResponse verificationCodeResponse = response.getBody();
        assertNotNull(verificationCodeResponse);
        assertEquals(verificationCodeExpirationSec, verificationCodeResponse.getVerificationCodeExpirationTime());
    }

    @Test
    @Order(4)
    public void checkVerificationCodeWhenInvalidRequestBodyAppliedShouldReturnBadRequestStatusCode() {
        VerificationCodeDto verificationCodeDto = requestDtoBuilder.createVerificationCodeDto(EndpointUrlAndConstants.TEST_VALUE);
        setAccessTokenToHeader(false);

        HttpEntity<VerificationCodeDto> entity = new HttpEntity<>(verificationCodeDto, headers);
        ResponseEntity<GeneralMessageErrorResponse> response = testRestTemplate
                .postForEntity(createURI(EndpointUrlAndConstants.CODE_VERIFICATION), entity,
                        GeneralMessageErrorResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        GeneralMessageErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Verification code can contain only 6 digits", errorResponse.getMessage());
    }

    @Test
    @Order(5)
    public void savePasswordWhenValidRequestBodyAppliedShouldReturnOkStatusCode() {
        modelUtil.setClientVerificationCodeId();
        PasswordDto passwordDto = requestDtoBuilder.createPasswordDto(EndpointUrlAndConstants.PASSWORD);
        setAccessTokenToHeader(false);

        HttpEntity<PasswordDto> entity = new HttpEntity<>(passwordDto, headers);
        ResponseEntity<ClientIdResponse> response = restTemplate
                .exchange(createURI(EndpointUrlAndConstants.SAVE_PASSWORD), HttpMethod.PATCH, entity,
                        ClientIdResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        ClientIdResponse clientIdResponse = response.getBody();
        assertNotNull(clientIdResponse);
        assertEquals(EndpointUrlAndConstants.CLIENT_ID, clientIdResponse.getClientId());
    }

    @Test
    @Order(6)
    public void savePasswordWhenInvalidPasswordAppliedShouldReturnBadRequestStatusCode() {
        PasswordDto passwordDto = requestDtoBuilder.createPasswordDto(EndpointUrlAndConstants.TEST_VALUE);
        setAccessTokenToHeader(false);

        HttpEntity<PasswordDto> entity = new HttpEntity<>(passwordDto, headers);
        ResponseEntity<GeneralMessageErrorResponse> response = restTemplate
                .exchange(createURI(EndpointUrlAndConstants.SAVE_PASSWORD), HttpMethod.PATCH, entity,
                        GeneralMessageErrorResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        GeneralMessageErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Password must contain at least one character from at least three character groups " +
                "{Upper/Lower 'Eng' letters, digits, special symbols}", errorResponse.getMessage());
    }

    @Test
    @Order(7)
    public void savePassportNumberWhenValidRequestBodyAppliedShouldReturnOkStatusCode() {
        PassportNumberDto passportNumberDto = requestDtoBuilder.createPassportNumberDto(EndpointUrlAndConstants.PASSPORT_NUMBER);
        setAccessTokenToHeader(false);

        HttpEntity<PassportNumberDto> entity = new HttpEntity<>(passportNumberDto, headers);
        ResponseEntity<SuccessResponse> response = restTemplate
                .exchange(createURI(EndpointUrlAndConstants.SAVE_PASSPORT_NUMBER), HttpMethod.PATCH, entity,
                        SuccessResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        SuccessResponse successResponse = response.getBody();
        assertNotNull(successResponse);
        assertEquals("Resident passport number saved successfully", successResponse.getMessage());
    }

    @Test
    @Order(8)
    public void savePassportNumberWhenNotRFResidentPassportNumberAppliedShouldReturnConflictStatusCode() {
        PassportNumberDto passportNumberDto = requestDtoBuilder
                .createPassportNumberDto(EndpointUrlAndConstants.TEST_VALUE);
        setAccessTokenToHeader(false);

        HttpEntity<PassportNumberDto> entity = new HttpEntity<>(passportNumberDto, headers);
        ResponseEntity<GeneralErrorTypeErrorResponse> response = restTemplate
                .exchange(createURI(EndpointUrlAndConstants.SAVE_PASSPORT_NUMBER), HttpMethod.PATCH, entity,
                        GeneralErrorTypeErrorResponse.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

        GeneralErrorTypeErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(GeneralErrorTypeErrorResponse.ErrorType.NOT_RF_RESIDENT, errorResponse.getErrorType());
    }

    @Test
    @Order(9)
    public void saveFullNameWhenValidRequestBodyAppliedShouldReturnOkStatusCode() {
        FullNameDto fullNameDto = requestDtoBuilder.createFullNameDto();
        setAccessTokenToHeader(false);

        HttpEntity<FullNameDto> entity = new HttpEntity<>(fullNameDto, headers);
        ResponseEntity<SuccessResponse> response = restTemplate
                .exchange(createURI(EndpointUrlAndConstants.SAVE_FULL_NAME), HttpMethod.PATCH, entity, SuccessResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        SuccessResponse successResponse = response.getBody();
        assertNotNull(successResponse);
        assertEquals("Resident full name saved successfully", successResponse.getMessage());
    }

    @Test
    @Order(10)
    public void saveFullNameWhenExpiredAccessTokenAppliedShouldReturnUnauthorizedStatusCode() {
        FullNameDto fullNameDto = requestDtoBuilder.createFullNameDto();
        setAccessTokenToHeader(true);

        HttpEntity<FullNameDto> entity = new HttpEntity<>(fullNameDto, headers);
        ResponseEntity<GeneralErrorTypeErrorResponse> response = restTemplate
                .exchange(createURI(EndpointUrlAndConstants.SAVE_FULL_NAME), HttpMethod.PATCH, entity,
                        GeneralErrorTypeErrorResponse.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

        GeneralErrorTypeErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(GeneralErrorTypeErrorResponse.ErrorType.EXPIRED_TOKEN, errorResponse.getErrorType());
    }

    @Test
    @Order(11)
    public void saveSecurityQuestionAnswerWhenValidRequestBodyAppliedShouldReturnOkStatusCode() {
        SecurityQuestionAnswerDto questionAnswerDto = requestDtoBuilder.createSecurityQuestionAnswerDto();
        setAccessTokenToHeader(false);

        HttpEntity<SecurityQuestionAnswerDto> entity = new HttpEntity<>(questionAnswerDto, headers);
        ResponseEntity<SuccessResponse> response = restTemplate
                .exchange(createURI(EndpointUrlAndConstants.SAVE_SECURITY_QUESTION_ANSWER), HttpMethod.PATCH, entity,
                        SuccessResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        SuccessResponse successResponse = response.getBody();
        assertNotNull(successResponse);
        assertEquals("Resident security question and answer saved successfully", successResponse.getMessage());
    }

    @Test
    @Order(12)
    public void saveSecurityQuestionAnswerWhenAuthorizationHeaderIsMissedShouldReturnForbiddenStatusCode() {
        SecurityQuestionAnswerDto questionAnswerDto = requestDtoBuilder.createSecurityQuestionAnswerDto();

        HttpEntity<SecurityQuestionAnswerDto> entity = new HttpEntity<>(questionAnswerDto, headers);
        ResponseEntity<GeneralErrorTypeErrorResponse> response = restTemplate
                .exchange(createURI(EndpointUrlAndConstants.SAVE_SECURITY_QUESTION_ANSWER), HttpMethod.PATCH, entity,
                        GeneralErrorTypeErrorResponse.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

        GeneralErrorTypeErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(GeneralErrorTypeErrorResponse.ErrorType.NOT_ENOUGH_RIGHTS, errorResponse.getErrorType());
    }

    @Test
    @Order(13)
    public void acceptRBSSRulesWhenValidRequestBodyAppliedShouldReturnOkStatusCode() {
        AcceptRBSSRulesDto acceptRBSSRulesDto = requestDtoBuilder.createAcceptRBSSRulesDto(true);
        setAccessTokenToHeader(false);

        HttpEntity<AcceptRBSSRulesDto> entity = new HttpEntity<>(acceptRBSSRulesDto, headers);
        ResponseEntity<SuccessResponse> response = restTemplate
                .exchange(createURI(EndpointUrlAndConstants.ACCEPT_RBSS_RULES), HttpMethod.PATCH, entity,
                        SuccessResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        SuccessResponse successResponse = response.getBody();
        assertNotNull(successResponse);
        assertEquals("Client registered successfully", successResponse.getMessage());
    }

    @Test
    @Order(14)
    public void acceptRBSSRulesWhenInvalidRequestBodyAppliedShouldReturnBadRequestStatusCode() {
        AcceptRBSSRulesDto acceptRBSSRulesDto = requestDtoBuilder.createAcceptRBSSRulesDto(false);
        setAccessTokenToHeader(false);

        HttpEntity<AcceptRBSSRulesDto> entity = new HttpEntity<>(acceptRBSSRulesDto, headers);
        ResponseEntity<GeneralMessageErrorResponse> response = restTemplate
                .exchange(createURI(EndpointUrlAndConstants.ACCEPT_RBSS_RULES), HttpMethod.PATCH, entity,
                        GeneralMessageErrorResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        GeneralMessageErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("RBSS_SHOULD_BE_ACCEPTED", errorResponse.getMessage());
    }

    private void validateAccessToken(String accessToken) {
        Claims claims = jwtUtil.getClaimsFromJwt(accessToken);
        assertTrue(claims.containsKey("authorities"));

        assertNotNull(claims.getSubject());
        assertEquals(EndpointUrlAndConstants.CLIENT_ID, claims.getSubject());

        assertNotNull(claims.getIssuer());
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());

        assertTrue(claims.getIssuedAt().compareTo(new Date()) < 0);
        assertTrue(claims.getExpiration().compareTo(new Date()) > 0);
    }

    private String createURI(String url) {
        return String.format("http://localhost:%s/%s", port, url);
    }

    private void setAccessTokenToHeader(boolean expired) {
        String accessToken = expired ? tokenBuilder.createExpiredAccessTokenWithClientId(EndpointUrlAndConstants.CLIENT_ID) :
            tokenBuilder.createAccessTokenWithClientId(EndpointUrlAndConstants.CLIENT_ID);

        String authHeaderValue = tokenBuilder.getAuthorizationHeaderValue(accessToken);
        headers.add(HttpHeaders.AUTHORIZATION, authHeaderValue);
    }
}
