package com.rmn.toolkit.user.registration.controller;

import com.rmn.toolkit.user.registration.UserRegistrationApplication;
import com.rmn.toolkit.user.registration.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.user.registration.dto.response.success.RulesTextResponse;
import com.rmn.toolkit.user.registration.testUtil.EndpointUrlAndConstants;
import com.rmn.toolkit.user.registration.testUtil.ModelUtil;
import com.rmn.toolkit.user.registration.testUtil.TokenBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("integration-test")
@SpringBootTest(
        classes = UserRegistrationApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class RulesQueryControllerIntegrationTest {
    @LocalServerPort
    private Integer port;
    @Autowired
    private TestRestTemplate restTemplate;
    private RestTemplate getRestTemplate;
    private final HttpHeaders headers;
    @Autowired
    private TokenBuilder tokenBuilder;
    @Autowired
    private ModelUtil modelUtil;

    public RulesQueryControllerIntegrationTest() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @BeforeEach
    public void setUp() {
        getRestTemplate = restTemplate.getRestTemplate();
        getRestTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Test
    public void getPrivacyPolicyTextWhenValidRequestAppliedShouldReturnOkStatusCode() {
        HttpEntity<String> entity = new HttpEntity<>("params", headers);
        ResponseEntity<RulesTextResponse> response = getRestTemplate
                .exchange(createURI(EndpointUrlAndConstants.GET_PRIVACY_POLICY), HttpMethod.GET, entity,
                        RulesTextResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        RulesTextResponse rulesTextResponse = response.getBody();
        assertNotNull(rulesTextResponse);
        assertNotNull(rulesTextResponse.getText());
    }

    @Test
    public void getRBSSRulesTextWhenValidRequestAppliedShouldReturnOkStatusCode() {
        modelUtil.resetRegisteredClient();
        setAccessTokenToHeader(false);

        HttpEntity<String> entity = new HttpEntity<>("params", headers);
        ResponseEntity<RulesTextResponse> response = getRestTemplate
                .exchange(createURI(EndpointUrlAndConstants.GET_RBSS_RULES), HttpMethod.GET, entity,
                        RulesTextResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        RulesTextResponse rulesTextResponse = response.getBody();
        assertNotNull(rulesTextResponse);
        assertNotNull(rulesTextResponse.getText());
    }

    @Test
    public void getRBSSRulesTextWhenExpiredAccessTokenAppliedShouldReturnUnauthorizedStatusCode() {
        setAccessTokenToHeader(true);

        HttpEntity<String> entity = new HttpEntity<>("params", headers);
        ResponseEntity<GeneralErrorTypeErrorResponse> response = getRestTemplate
                .exchange(createURI(EndpointUrlAndConstants.GET_RBSS_RULES), HttpMethod.GET, entity,
                        GeneralErrorTypeErrorResponse.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

        GeneralErrorTypeErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(GeneralErrorTypeErrorResponse.ErrorType.EXPIRED_TOKEN, errorResponse.getErrorType());
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
