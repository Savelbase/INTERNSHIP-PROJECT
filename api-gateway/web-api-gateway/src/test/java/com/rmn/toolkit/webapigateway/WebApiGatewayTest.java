package com.rmn.toolkit.webapigateway;

import com.rmn.toolkit.webapigateway.dto.response.ErrorResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(
        classes = {WebApiGatewayApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class WebApiGatewayTest {
    @LocalServerPort
    private Integer port;
    @Autowired
    private TestRestTemplate restTemplate;
    private RestTemplate getRestTemplate;
    private final HttpHeaders headers;

    public WebApiGatewayTest() {
        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @BeforeEach
    public void setUp() {
        getRestTemplate = restTemplate.getRestTemplate();
        getRestTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Test
    @SneakyThrows
    void authFilterTestWithAuthorization(){
        headers.add("Authorization" , "Bearer" );
        HttpEntity<String> entity = new HttpEntity<>("params", headers);
        ResponseEntity<ErrorResponse> response = getRestTemplate
                .exchange(createURI() , HttpMethod.GET , entity , ErrorResponse.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED , response.getStatusCode());
    }

    @Test
    @SneakyThrows
    void authFilterTestWithoutAuth(){
        HttpEntity<String> entity = new HttpEntity<>("params", headers);
        ResponseEntity<ErrorResponse> response = getRestTemplate
                .exchange(createURI() , HttpMethod.GET , entity , ErrorResponse.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED , response.getStatusCode());
    }

    private String createURI() {
        return String.format("http://localhost:%s/api/v1/users", port);
    }
}
