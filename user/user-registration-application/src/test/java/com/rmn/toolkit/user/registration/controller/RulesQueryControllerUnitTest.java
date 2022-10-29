package com.rmn.toolkit.user.registration.controller;

import com.rmn.toolkit.user.registration.advice.GlobalExceptionHandler;
import com.rmn.toolkit.user.registration.security.SecurityConfig;
import com.rmn.toolkit.user.registration.security.SecurityUtil;
import com.rmn.toolkit.user.registration.security.jwt.JwtUtil;
import com.rmn.toolkit.user.registration.security.jwt.user.CurrentUserDetailsService;
import com.rmn.toolkit.user.registration.service.RulesQueryService;
import com.rmn.toolkit.user.registration.testUtil.EndpointUrlAndConstants;
import com.rmn.toolkit.user.registration.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureWebMvc
@SpringBootTest(classes = {
        SecurityConfig.class,
        CurrentUserDetailsService.class,
        JwtUtil.class,
        GlobalExceptionHandler.class,
        ResponseUtil.class,
        RulesQueryController.class,
        RulesQueryService.class,
        SecurityUtil.class
})
@MockBeans({
        @MockBean(RulesQueryService.class),
        @MockBean(SecurityUtil.class)
})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RulesQueryControllerUnitTest {
    private final MockMvc mockMvc;
    private final RulesQueryService service;
    private final SecurityUtil securityUtil;

    @Test
    public void getPrivacyPolicyTextWhenValidRequestAppliedShouldReturnOkStatusCode() throws Exception {
        when(service.getPrivacyPolicyText()).thenReturn(EndpointUrlAndConstants.TEST_VALUE);

        mockMvc.perform(get(EndpointUrlAndConstants.GET_PRIVACY_POLICY))
                .andExpect(status().isOk());

        verify(service, times(1)).getPrivacyPolicyText();
    }

    @Test
    @WithMockUser(authorities = "REGISTRATION")
    public void getRBSSRulesTextWithAuthorization() throws Exception {
        when(securityUtil.getClientIdFromSecurityContext()).thenReturn(EndpointUrlAndConstants.TEST_VALUE);
        when(service.getRBSSRulesText(anyString())).thenReturn(EndpointUrlAndConstants.TEST_VALUE);

        mockMvc.perform(get(EndpointUrlAndConstants.GET_RBSS_RULES))
                .andExpect(status().isOk());

        verify(service, times(1)).getRBSSRulesText(anyString());
    }

    @Test
    public void getRBSSRulesTextWithoutAuthorization() throws Exception {
        mockMvc.perform(get(EndpointUrlAndConstants.GET_RBSS_RULES))
                .andExpect(status().isForbidden());
    }
}
