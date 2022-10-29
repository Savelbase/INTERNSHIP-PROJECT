package com.rmn.toolkit.user.command.controller;

import com.google.gson.Gson;
import com.rmn.toolkit.user.command.advice.GlobalExceptionHandler;
import com.rmn.toolkit.user.command.dto.request.CodeDto;
import com.rmn.toolkit.user.command.dto.request.NotificationDto;
import com.rmn.toolkit.user.command.security.SecurityConfig;
import com.rmn.toolkit.user.command.security.SecurityUtil;
import com.rmn.toolkit.user.command.security.jwt.JwtUserDetailsService;
import com.rmn.toolkit.user.command.security.jwt.JwtUtil;
import com.rmn.toolkit.user.command.service.UserDataService;
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
        ResponseUtil.class,
        UserDataController.class,
        UserDataService.class,
        ClientUtil.class,
        SecurityUtil.class,
        RequestDtoBuilder.class
}, properties = "spring.cloud.config.enabled=false")
@MockBeans({
        @MockBean(UserDataService.class),
        @MockBean(ClientUtil.class),
        @MockBean(SecurityUtil.class),
        @MockBean(ResponseUtil.class)
})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDataControllerUnitTest {
    private final MockMvc mockMvc;
    private final RequestDtoBuilder requestDtoBuilder;
    private final UserDataService service;
    private final ClientUtil clientUtil;
    private final SecurityUtil securityUtil;

    @Test
    @WithMockUser(authorities = "USER_EDIT")
    public void createPinCodeWithAuthorization() throws Exception {
        CodeDto codeDto = requestDtoBuilder.createCodeDto(EndpointUrlAndConstants.CODE);
        String json = convertRequestDtoToJson(codeDto);

        when(securityUtil.getCurrentUserId()).thenReturn(EndpointUrlAndConstants.TEST_VALUE);
        doNothing().when(clientUtil).checkIfClientIsBlocked(anyString());
        doNothing().when(service).createPinCode(any(CodeDto.class), anyString());

        mockMvc.perform(patch(EndpointUrlAndConstants.CREATE_PIN_CODE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(securityUtil, times(1)).getCurrentUserId();
        verify(clientUtil, times(1)).checkIfClientIsBlocked(anyString());
        verify(service, times(1)).createPinCode(any(CodeDto.class), anyString());
    }

    @Test
    public void createPinCodeWithoutAuthorization() throws Exception {
        CodeDto codeDto = requestDtoBuilder.createCodeDto(EndpointUrlAndConstants.CODE);
        String json = convertRequestDtoToJson(codeDto);

        mockMvc.perform(patch(EndpointUrlAndConstants.CREATE_PIN_CODE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "USER_EDIT")
    public void changeEmailWithAuthorization() throws Exception {
        doNothing().when(service).changeEmail(anyString());
        mockMvc.perform(patch(EndpointUrlAndConstants.CHANGE_EMAIL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("email" , EndpointUrlAndConstants.TEST_EMAIL))
                .andExpect(status().isOk());
        verify(service, times(1)).changeEmail(anyString());
    }
    @Test
    public void changeEmailWithoutAuthorization() throws Exception {
        mockMvc.perform(patch(EndpointUrlAndConstants.CHANGE_EMAIL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("email" , EndpointUrlAndConstants.TEST_EMAIL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "USER_EDIT")
    public void changeNotificationStatusWithAuthorization() throws Exception {
        NotificationDto notificationDto = requestDtoBuilder.createNotificationDto();
        String json = convertRequestDtoToJson(notificationDto);
        doNothing().when(service).changeNotification(any(NotificationDto.class));
        mockMvc.perform(patch(EndpointUrlAndConstants.CHANGE_NOTIFICATIONS_STATUS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
        verify(service, times(1)).changeNotification(any(NotificationDto.class));
    }

    @Test
    public void changeNotificationStatusWithoutAuthorization() throws Exception {
        NotificationDto notificationDto = requestDtoBuilder.createNotificationDto();
        String json = convertRequestDtoToJson(notificationDto);

        mockMvc.perform(patch(EndpointUrlAndConstants.CHANGE_NOTIFICATIONS_STATUS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isForbidden());
    }

    private <T> String convertRequestDtoToJson(T requestDto) {
        Gson gson = new Gson();
        return gson.toJson(requestDto);
    }
}
