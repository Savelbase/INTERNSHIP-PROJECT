package com.rmn.toolkit.user.command.controller;

import com.google.gson.Gson;
import com.rmn.toolkit.user.command.advice.GlobalExceptionHandler;
import com.rmn.toolkit.user.command.dto.request.ChangePasswordDto;
import com.rmn.toolkit.user.command.dto.request.SecurityQADto;
import com.rmn.toolkit.user.command.security.SecurityConfig;
import com.rmn.toolkit.user.command.security.SecurityUtil;
import com.rmn.toolkit.user.command.security.jwt.JwtUserDetailsService;
import com.rmn.toolkit.user.command.security.jwt.JwtUtil;
import com.rmn.toolkit.user.command.service.UserSecurityService;
import com.rmn.toolkit.user.command.testUtil.EndpointUrlAndConstants;
import com.rmn.toolkit.user.command.testUtil.RequestDtoBuilder;
import com.rmn.toolkit.user.command.util.ClientUtil;
import com.rmn.toolkit.user.command.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureWebMvc
@SpringBootTest(classes = {
        SecurityConfig.class,
        JwtUserDetailsService.class,
        JwtUtil.class,
        GlobalExceptionHandler.class,
        UserSecurityController.class,
        UserSecurityService.class,
        SecurityUtil.class,
        ClientUtil.class,
        ResponseUtil.class,
        RequestDtoBuilder.class
}, properties = "spring.cloud.config.enabled=false")
@MockBeans({
        @MockBean(UserSecurityService.class),
        @MockBean(ClientUtil.class),
        @MockBean(ResponseUtil.class)
})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserSecurityControllerUnitTest {
    private final MockMvc mockMvc;
    private final RequestDtoBuilder requestDtoBuilder;
    private final UserSecurityService service;

    @Test
    @WithMockUser(authorities = "USER_EDIT")
    public void changeQAWithAuthorization() throws Exception {
        SecurityQADto qaDto = requestDtoBuilder
                .createQADto(EndpointUrlAndConstants.TEST_VALUE , EndpointUrlAndConstants.TEST_VALUE);
        String json = convertRequestDtoToJson(qaDto);
        doNothing().when(service).changeQA(any(SecurityQADto.class), anyString());

        mockMvc.perform(patch(EndpointUrlAndConstants.CHANGE_SECURITY_QUESTION_ANSWER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
        verify(service, times(1)).changeQA(any(SecurityQADto.class), anyString());
    }
    @Test
    public void changeQAWithoutAuthorization() throws Exception {
        SecurityQADto qaDto = requestDtoBuilder
                .createQADto(EndpointUrlAndConstants.TEST_VALUE , EndpointUrlAndConstants.TEST_VALUE);
        String json = convertRequestDtoToJson(qaDto);
        mockMvc.perform(patch(EndpointUrlAndConstants.CHANGE_SECURITY_QUESTION_ANSWER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "USER_EDIT")
    public void changePasswordWithAuth() throws Exception {
        ChangePasswordDto passwordDto = requestDtoBuilder.createChangePassDto();
        String json = convertRequestDtoToJson(passwordDto);
        doNothing().when(service).changePassword(any(ChangePasswordDto.class), anyString());

        mockMvc.perform(patch(EndpointUrlAndConstants.CHANGE_SECURITY_PASSWORD)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
        verify(service, times(1)).changePassword(any(ChangePasswordDto.class), anyString());
    }

    @Test
    public void changePasswordWithoutAuth() throws Exception {
        ChangePasswordDto passwordDto = requestDtoBuilder.createChangePassDto();
        String json = convertRequestDtoToJson(passwordDto);

        mockMvc.perform(patch(EndpointUrlAndConstants.CHANGE_SECURITY_PASSWORD)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    private <T> String convertRequestDtoToJson(T requestDto) {
        Gson gson = new Gson();
        return gson.toJson(requestDto);
    }
}
