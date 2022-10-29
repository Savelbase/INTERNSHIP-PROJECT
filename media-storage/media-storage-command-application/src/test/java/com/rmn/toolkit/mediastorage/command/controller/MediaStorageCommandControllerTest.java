package com.rmn.toolkit.mediastorage.command.controller;

import com.rmn.toolkit.mediastorage.command.advice.GlobalExceptionHandler;
import com.rmn.toolkit.mediastorage.command.security.SecurityConfig;
import com.rmn.toolkit.mediastorage.command.security.SecurityUtil;
import com.rmn.toolkit.mediastorage.command.security.jwt.JwtUserDetailsService;
import com.rmn.toolkit.mediastorage.command.security.jwt.JwtUtil;
import com.rmn.toolkit.mediastorage.command.service.MediaStorageCommandService;
import com.rmn.toolkit.mediastorage.command.testUtil.EndpointUrlAndConstants;
import com.rmn.toolkit.mediastorage.command.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureWebMvc
@SpringBootTest(classes = {
        SecurityConfig.class,
        JwtUserDetailsService.class,
        JwtUtil.class,
        GlobalExceptionHandler.class,
        ResponseUtil.class,
        MediaStorageCommandController.class,
        MediaStorageCommandService.class,
        SecurityUtil.class
}, properties = "spring.cloud.config.enabled=false")
@MockBeans({
        @MockBean(MediaStorageCommandService.class),
        @MockBean(SecurityUtil.class)
})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MediaStorageCommandControllerTest {
    private final MockMvc mockMvc;
    private final MediaStorageCommandService service;
    private final SecurityUtil securityUtil;

    @Test
    @WithMockUser(authorities = "UPLOAD_IMAGE")
    public void uploadFileWithAuthorization() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile(EndpointUrlAndConstants.TEST_VALUE,
                EndpointUrlAndConstants.TEST_VALUE.getBytes());

        when(securityUtil.getCurrentUserId()).thenReturn(EndpointUrlAndConstants.TEST_VALUE);
        doNothing().when(service).saveFile(anyString(), any(MultipartFile.class));

        mockMvc.perform(MockMvcRequestBuilders.multipart(EndpointUrlAndConstants.UPLOAD_FILE)
                        .file("file", multipartFile.getBytes()))
                .andExpect(status().isAccepted());

        verify(securityUtil, times(1)).getCurrentUserId();
        verify(service, times(1)).saveFile(anyString(), any(MultipartFile.class));
    }

    @Test
    public void uploadFileWithoutAuthorization() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile(EndpointUrlAndConstants.TEST_VALUE,
                EndpointUrlAndConstants.TEST_VALUE.getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart(EndpointUrlAndConstants.UPLOAD_FILE)
                        .file("file", multipartFile.getBytes()))
                .andExpect(status().isForbidden());
    }
}
