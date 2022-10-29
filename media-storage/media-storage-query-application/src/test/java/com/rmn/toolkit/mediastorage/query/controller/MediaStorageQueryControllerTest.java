package com.rmn.toolkit.mediastorage.query.controller;

import com.rmn.toolkit.mediastorage.query.advice.GlobalExceptionHandler;
import com.rmn.toolkit.mediastorage.query.security.SecurityConfig;
import com.rmn.toolkit.mediastorage.query.security.jwt.JwtUserDetailsService;
import com.rmn.toolkit.mediastorage.query.security.jwt.JwtUtil;
import com.rmn.toolkit.mediastorage.query.service.MediaStorageQueryService;
import com.rmn.toolkit.mediastorage.query.testUtil.EndpointUrlAndConstants;
import com.rmn.toolkit.mediastorage.query.util.ResponseUtil;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureMockMvc
@AutoConfigureWebMvc
@SpringBootTest(classes = {
        SecurityConfig.class,
        JwtUserDetailsService.class,
        JwtUtil.class,
        GlobalExceptionHandler.class,
        ResponseUtil.class,
        MediaStorageQueryController.class,
        MediaStorageQueryService.class
}, properties = "spring.cloud.config.enabled=false")
@MockBeans({
        @MockBean(MediaStorageQueryService.class)
})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MediaStorageQueryControllerTest {
    private final MockMvc mockMvc;
    private final MediaStorageQueryService service;

    @Test
    @WithMockUser(authorities = "VIEW_IMAGE")
    public void loadFileByClientIdWithAuthorization() throws Exception {
        when(service.getFileByClientId(anyString())).thenReturn(any(byte[].class));

        mockMvc.perform(get(EndpointUrlAndConstants.GET_FILE_BY_CLIENT_ID))
                .andExpect(status().isOk());

        verify(service, times(1)).getFileByClientId(anyString());
    }

    @Test
    public void loadFileByClientIdWithoutAuthorization() throws Exception {
        mockMvc.perform(get(EndpointUrlAndConstants.GET_FILE_BY_CLIENT_ID))
                .andExpect(status().isForbidden());
    }
}
