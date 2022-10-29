package com.rmn.toolkit.user.command.controller;

import com.google.gson.Gson;
import com.rmn.toolkit.user.command.advice.GlobalExceptionHandler;
import com.rmn.toolkit.user.command.dto.request.CodeDto;
import com.rmn.toolkit.user.command.dto.request.PassportNumberDto;
import com.rmn.toolkit.user.command.dto.response.success.AccessTokenResponse;
import com.rmn.toolkit.user.command.dto.response.success.VerificationCodeWithTokenResponse;
import com.rmn.toolkit.user.command.model.Client;
import com.rmn.toolkit.user.command.security.SecurityConfig;
import com.rmn.toolkit.user.command.security.SecurityUtil;
import com.rmn.toolkit.user.command.security.jwt.JwtUserDetailsService;
import com.rmn.toolkit.user.command.security.jwt.JwtUtil;
import com.rmn.toolkit.user.command.service.PinCodeResetService;
import com.rmn.toolkit.user.command.testUtil.EndpointUrlAndConstants;
import com.rmn.toolkit.user.command.testUtil.RequestDtoBuilder;
import com.rmn.toolkit.user.command.testUtil.ResponseDtoBuilder;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureWebMvc
@SpringBootTest(classes = {
        SecurityConfig.class,
        JwtUserDetailsService.class,
        JwtUtil.class,
        GlobalExceptionHandler.class,
        UserResetPinController.class,
        PinCodeResetService.class,
        SecurityUtil.class,
        ResponseUtil.class,
        RequestDtoBuilder.class,
        ClientUtil.class,
        ResponseDtoBuilder.class
}, properties = "spring.cloud.config.enabled=false")
@MockBeans({
        @MockBean(PinCodeResetService.class),
        @MockBean(SecurityUtil.class),
        @MockBean(ResponseUtil.class),
        @MockBean(ClientUtil.class)
})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserResetPinCodeControllerUnitTest {
    private final MockMvc mockMvc;
    private final RequestDtoBuilder requestDtoBuilder;
    private final ResponseDtoBuilder responseDtoBuilder;
    private final PinCodeResetService service;
    private final SecurityUtil securityUtil;
    private final ClientUtil clientUtil;
    private final ResponseUtil responseUtil;

    @Test
    public void createVerificationCodeWhenValidParameterAppliedShouldReturnCreatedStatusCode() throws Exception {
        PassportNumberDto passportNumberDto = requestDtoBuilder.createPassportDto(EndpointUrlAndConstants.PASSPORT_NUMBER);
        String json = convertRequestDtoToJson(passportNumberDto);

        Client client = requestDtoBuilder.createClient();
        when(clientUtil.findClientByPassportNumber(anyString())).thenReturn(client);
        when(service.createVerificationCode(anyString())).thenReturn(EndpointUrlAndConstants.TEST_VALUE);
        when(service.checkClientByMobilePhoneAndGenerateToken(any(Client.class))).thenReturn(EndpointUrlAndConstants.TEST_VALUE);
        when(responseUtil.getVerificationCodeWithTokenResponse(anyString(), anyString()))
                .thenReturn(any(VerificationCodeWithTokenResponse.class));

        mockMvc.perform(post(EndpointUrlAndConstants.PIN_RESET_CODE_GENERATION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(clientUtil, times(1)).findClientByPassportNumber(anyString());
        verify(clientUtil, times(1)).findClientByPassportNumber(anyString());
        verify(service, times(1)).checkClientByMobilePhoneAndGenerateToken(any(Client.class));
        verify(responseUtil, times(1)).getVerificationCodeWithTokenResponse(anyString(), anyString());
    }

    @Test
    @WithMockUser(authorities = "USER_EDIT")
    public void checkVerificationCodeWithAuthorization() throws Exception {
        CodeDto codeDto = requestDtoBuilder.createCodeDto(EndpointUrlAndConstants.CODE);
        String json = convertRequestDtoToJson(codeDto);

        Client client = requestDtoBuilder.createClient();
        AccessTokenResponse accessTokenResponse = responseDtoBuilder.createAccessTokenResponse();

        when(securityUtil.getCurrentUserId()).thenReturn(EndpointUrlAndConstants.TEST_VALUE);
        doNothing().when(service).checkVerificationCode(anyString(), anyString());
        when(clientUtil.findClientById(anyString())).thenReturn(client);
        when(service.createAccessTokenWithPinResetAuthorityType(any(Client.class))).thenReturn(EndpointUrlAndConstants.TEST_VALUE);
        when(responseUtil.createAccessToken(anyString())).thenReturn(accessTokenResponse);

        mockMvc.perform(post(EndpointUrlAndConstants.PIN_RESET_CODE_VERIFICATION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(securityUtil, times(1)).getCurrentUserId();
        verify(service, times(1)).checkVerificationCode(anyString(), anyString());
        verify(clientUtil, times(1)).findClientById(anyString());
        verify(service, times(1)).createAccessTokenWithPinResetAuthorityType(any(Client.class));
        verify(responseUtil, times(1)).createAccessToken(anyString());
    }

    @Test
    public void checkVerificationCodeWithoutAuthorization() throws Exception {
        CodeDto codeDto = requestDtoBuilder.createCodeDto(EndpointUrlAndConstants.CODE);
        String json = convertRequestDtoToJson(codeDto);

        mockMvc.perform(post(EndpointUrlAndConstants.PIN_RESET_CODE_VERIFICATION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "PIN_CODE_RESET")
    public void createPasswordWithAuthorization() throws Exception {
        CodeDto codeDto = requestDtoBuilder.createCodeDto(EndpointUrlAndConstants.CODE);
        String json = convertRequestDtoToJson(codeDto);

        when(securityUtil.getCurrentUserId()).thenReturn(EndpointUrlAndConstants.TEST_VALUE);
        doNothing().when(service).savePinCode(any(CodeDto.class), anyString());

        mockMvc.perform(patch(EndpointUrlAndConstants.PIN_RESET)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(securityUtil, times(1)).getCurrentUserId();
        verify(service, times(1)).savePinCode(any(CodeDto.class), anyString());
    }

    @Test
    public void createPasswordWithoutAuthorization() throws Exception {
        CodeDto codeDto = requestDtoBuilder.createCodeDto(EndpointUrlAndConstants.CODE);
        String json = convertRequestDtoToJson(codeDto);

        mockMvc.perform(patch(EndpointUrlAndConstants.PIN_RESET)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

        private <T> String convertRequestDtoToJson(T requestDto) {
        Gson gson = new Gson();
        return gson.toJson(requestDto);
    }
}
