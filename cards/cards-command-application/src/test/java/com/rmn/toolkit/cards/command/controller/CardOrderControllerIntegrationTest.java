package com.rmn.toolkit.cards.command.controller;

import com.rmn.toolkit.cards.command.CardsCommandApplication;
import com.rmn.toolkit.cards.command.dto.request.CardOrderDto;
import com.rmn.toolkit.cards.command.dto.request.CardOrderStatusDto;
import com.rmn.toolkit.cards.command.dto.response.error.GeneralErrorTypeErrorResponse;
import com.rmn.toolkit.cards.command.dto.response.error.GeneralMessageErrorResponse;
import com.rmn.toolkit.cards.command.dto.response.success.CardOrderIdResponse;
import com.rmn.toolkit.cards.command.dto.response.success.SuccessResponse;
import com.rmn.toolkit.cards.command.model.CardOrder;
import com.rmn.toolkit.cards.command.model.type.CardOrderStatusType;
import com.rmn.toolkit.cards.command.repository.CardOrderRepository;
import com.rmn.toolkit.cards.command.testUtil.EndpointUrlAndConstants;
import com.rmn.toolkit.cards.command.testUtil.RequestDtoBuilder;
import com.rmn.toolkit.cards.command.testUtil.TokenBuilder;
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
        classes = CardsCommandApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class CardOrderControllerIntegrationTest {
    @LocalServerPort
    private Integer port;
    @Autowired
    private TestRestTemplate testRestTemplate;
    private RestTemplate restTemplate;
    private final HttpHeaders headers;
    @Autowired
    private TokenBuilder tokenBuilder;
    @Autowired
    private RequestDtoBuilder requestDtoBuilder;
    @Autowired
    private CardOrderRepository cardOrderRepository;

    public CardOrderControllerIntegrationTest() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @BeforeEach
    public void setUp() {
        restTemplate = testRestTemplate.getRestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Test
    public void createCardOrderWhenValidRequestBodyAppliedShouldReturnCreatedStatusCode() {
        CardOrderDto cardOrderDto = requestDtoBuilder.createCardOrderDto(true);
        setAccessTokenToHeader(false);

        HttpEntity<CardOrderDto> entity = new HttpEntity<>(cardOrderDto, headers);
        ResponseEntity<CardOrderIdResponse> response = testRestTemplate
                .postForEntity(createURI(EndpointUrlAndConstants.CARD_ORDER_URL), entity, CardOrderIdResponse.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        CardOrderIdResponse cardOrderIdResponse = response.getBody();
        assertNotNull(cardOrderIdResponse);
    }

    @Test
    public void createCardOrderWhenInvalidRequestBodyAppliedShouldReturnBadRequestStatusCode() {
        CardOrderDto cardOrderDto = requestDtoBuilder.createCardOrderDto(false);
        setAccessTokenToHeader(false);

        HttpEntity<CardOrderDto> entity = new HttpEntity<>(cardOrderDto, headers);
        ResponseEntity<GeneralMessageErrorResponse> response = testRestTemplate
                .postForEntity(createURI(EndpointUrlAndConstants.CARD_ORDER_URL), entity, GeneralMessageErrorResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        GeneralMessageErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("CONDITIONS_MUST_BE_ACCEPTED", errorResponse.getMessage());
    }

    @Test
    public void changeCardOrderStatusWhenValidRequestBodyAppliedShouldReturnOkStatusCode() {
        CardOrder cardOrder = requestDtoBuilder.createCardOrder();
        CardOrder savedCardOrder = cardOrderRepository.save(cardOrder);
        CardOrderStatusDto cardOrderStatusDto = requestDtoBuilder
                .createValidCardOrderStatusDto(savedCardOrder.getId(), CardOrderStatusType.APPROVED);

        setAccessTokenToHeader(false);

        HttpEntity<CardOrderStatusDto> entity = new HttpEntity<>(cardOrderStatusDto, headers);
        ResponseEntity<SuccessResponse> response = restTemplate
                .exchange(createURI(EndpointUrlAndConstants.CARD_ORDER_URL), HttpMethod.PATCH, entity, SuccessResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        SuccessResponse successResponse = response.getBody();
        assertNotNull(successResponse);
        assertEquals("Card order status changed successfully", successResponse.getMessage());
    }

    @Test
    public void changeCardOrderStatusWhenInvalidCardOrderIdAppliedShouldReturnNotFoundStatusCode() {
        CardOrderStatusDto cardOrderStatusDto = requestDtoBuilder.createCardOrderStatusDto(CardOrderStatusType.APPROVED);
        setAccessTokenToHeader(false);

        HttpEntity<CardOrderStatusDto> entity = new HttpEntity<>(cardOrderStatusDto, headers);
        ResponseEntity<GeneralErrorTypeErrorResponse> response = restTemplate
                .exchange(createURI(EndpointUrlAndConstants.CARD_ORDER_URL), HttpMethod.PATCH, entity, GeneralErrorTypeErrorResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        GeneralErrorTypeErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(GeneralErrorTypeErrorResponse.ErrorType.CARD_ORDER_NOT_FOUND, errorResponse.getErrorType());
    }

    private String createURI(String url) {
        return String.format("http://localhost:%s/%s", port, url);
    }

    private void setAccessTokenToHeader(boolean expired) {
        String accessToken = expired ?
                tokenBuilder.createExpiredAccessTokenWithClientId(EndpointUrlAndConstants.CLIENT_ID) :
                tokenBuilder.createAccessTokenWithClientId(EndpointUrlAndConstants.CLIENT_ID);

        String authHeaderValue = tokenBuilder.getAuthorizationHeaderValue(accessToken);
        headers.add(HttpHeaders.AUTHORIZATION, authHeaderValue);
    }
}
