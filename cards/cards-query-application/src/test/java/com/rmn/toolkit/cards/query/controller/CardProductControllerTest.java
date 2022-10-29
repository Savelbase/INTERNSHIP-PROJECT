package com.rmn.toolkit.cards.query.controller;

import com.rmn.toolkit.cards.query.repository.CardProductRepository;
import com.rmn.toolkit.cards.query.testUtil.EndpointUrlAndConstants;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureWebMvc
@SpringBootTest(classes = {
        CardProductController.class,
        CardProductRepository.class
}, properties = "spring.cloud.config.enabled=false")
@MockBeans({
        @MockBean(CardProductRepository.class),
})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CardProductControllerTest {
    private final MockMvc mockMvc;

    @Test
    @WithMockUser
    public void getAllCardProductsWithAuthorization() throws Exception {
        mockMvc.perform(get(EndpointUrlAndConstants.GET_CARD_PRODUCTS)
                        .header(HttpHeaders.AUTHORIZATION, EndpointUrlAndConstants.TEST_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllCardProductsWithoutAuthorization() throws Exception {
        mockMvc.perform(get(EndpointUrlAndConstants.GET_CARD_PRODUCTS)
                        .header(HttpHeaders.AUTHORIZATION, EndpointUrlAndConstants.TEST_VALUE))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void getCardProductByIdWithAuthorization() throws Exception {
        mockMvc.perform(get(EndpointUrlAndConstants.GET_CARD_PRODUCT_BY_CARD_ID)
                        .header(HttpHeaders.AUTHORIZATION, EndpointUrlAndConstants.TEST_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void getCardProductByIdWithoutAuthorization() throws Exception {
        mockMvc.perform(get(EndpointUrlAndConstants.GET_CARD_PRODUCT_BY_CARD_ID)
                        .header(HttpHeaders.AUTHORIZATION, EndpointUrlAndConstants.TEST_VALUE))
                .andExpect(status().isUnauthorized());
    }

}
