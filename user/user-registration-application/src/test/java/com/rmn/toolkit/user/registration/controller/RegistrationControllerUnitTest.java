package com.rmn.toolkit.user.registration.controller;

import com.google.gson.Gson;
import com.rmn.toolkit.user.registration.advice.GlobalExceptionHandler;
import com.rmn.toolkit.user.registration.dto.request.*;
import com.rmn.toolkit.user.registration.security.SecurityConfig;
import com.rmn.toolkit.user.registration.security.SecurityUtil;
import com.rmn.toolkit.user.registration.security.jwt.JwtUtil;
import com.rmn.toolkit.user.registration.security.jwt.user.CurrentUserDetailsService;
import com.rmn.toolkit.user.registration.service.RegistrationService;
import com.rmn.toolkit.user.registration.testUtil.EndpointUrlAndConstants;
import com.rmn.toolkit.user.registration.testUtil.RequestDtoBuilder;
import com.rmn.toolkit.user.registration.util.ClientUtil;
import com.rmn.toolkit.user.registration.util.ResponseUtil;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureWebMvc
@SpringBootTest(classes = {
        SecurityConfig.class,
        CurrentUserDetailsService.class,
        JwtUtil.class,
        GlobalExceptionHandler.class,
        RegistrationController.class,
        RegistrationService.class,
        SecurityUtil.class,
        ClientUtil.class,
        ResponseUtil.class,
        RequestDtoBuilder.class
})
@MockBeans({
        @MockBean(RegistrationService.class),
        @MockBean(SecurityUtil.class),
        @MockBean(ClientUtil.class),
        @MockBean(ResponseUtil.class)
})
@RequiredArgsConstructor(onConstructor = @__(
        @Autowired))
public class RegistrationControllerUnitTest {
    private final MockMvc mockMvc;
    private final RequestDtoBuilder requestDtoBuilder;
    private final RegistrationService service;
    private final SecurityUtil securityUtil;
    private final ClientUtil clientUtil;
    private final ResponseUtil responseUtil;

    @Test
    public void checkMobilePhoneAndGenerateTokenWhenValidRequestBodyAppliedShouldReturnCreatedStatusCode() throws Exception {
        MobilePhoneDto mobilePhoneDto = requestDtoBuilder
                .createMobilePhoneDto(EndpointUrlAndConstants.CLIENT_MOBILE_PHONE);
        String json = convertRequestDtoToJson(mobilePhoneDto);

        when(service.checkMobilePhoneAndGenerateToken(any(MobilePhoneDto.class)))
                .thenReturn(EndpointUrlAndConstants.TEST_VALUE);

        mockMvc.perform(post(EndpointUrlAndConstants.CHECK_MOBILE_PHONE_AND_GENERATE_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(service, times(1)).checkMobilePhoneAndGenerateToken(any(MobilePhoneDto.class));
        verify(responseUtil, times(1)).createAccessToken(anyString());
    }

    @Test
    public void checkMobilePhoneAndGenerateTokenWhenInvalidRequestBodyAppliedShouldReturnBadRequestStatusCode() throws Exception {
        MobilePhoneDto mobilePhoneDto = requestDtoBuilder.createMobilePhoneDto(EndpointUrlAndConstants.TEST_VALUE);
        String json = convertRequestDtoToJson(mobilePhoneDto);

        mockMvc.perform(post(EndpointUrlAndConstants.CHECK_MOBILE_PHONE_AND_GENERATE_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "REGISTRATION")
    public void createVerificationCodeWithAuthorization() throws Exception {
        when(securityUtil.getClientIdFromSecurityContext()).thenReturn(EndpointUrlAndConstants.TEST_VALUE);
        when(service.createVerificationCode(anyString())).thenReturn(EndpointUrlAndConstants.TEST_VALUE);

        mockMvc.perform(post(EndpointUrlAndConstants.CODE_GENERATION))
                .andExpect(status().isCreated());

        verify(service, times(1)).createVerificationCode(anyString());
        verify(responseUtil, times(1)).createVerificationCode(anyString());
    }

    @Test
    public void createVerificationCodeWithoutAuthorization() throws Exception {
        mockMvc.perform(post(EndpointUrlAndConstants.CODE_GENERATION))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "REGISTRATION")
    public void checkVerificationCodeWithAuthorization() throws Exception {
        VerificationCodeDto verificationCodeDto = requestDtoBuilder
                .createVerificationCodeDto(EndpointUrlAndConstants.VERIFICATION_CODE);
        String json = convertRequestDtoToJson(verificationCodeDto);

        when(securityUtil.getClientIdFromSecurityContext()).thenReturn(EndpointUrlAndConstants.TEST_VALUE);
        doNothing().when(service).checkVerificationCode(anyString(), anyString());

        mockMvc.perform(post(EndpointUrlAndConstants.CODE_VERIFICATION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(service, times(1)).checkVerificationCode(anyString(), anyString());
    }

    @Test
    public void checkVerificationCodeWithoutAuthorization() throws Exception {
        VerificationCodeDto verificationCodeDto = requestDtoBuilder
                .createVerificationCodeDto(EndpointUrlAndConstants.VERIFICATION_CODE);
        String json = convertRequestDtoToJson(verificationCodeDto);

        mockMvc.perform(post(EndpointUrlAndConstants.CODE_VERIFICATION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "REGISTRATION")
    public void savePasswordWithAuthorization() throws Exception {
        PasswordDto passwordDto = requestDtoBuilder.createPasswordDto(EndpointUrlAndConstants.PASSWORD);
        String json = convertRequestDtoToJson(passwordDto);

        when(securityUtil.getClientIdFromSecurityContext()).thenReturn(EndpointUrlAndConstants.TEST_VALUE);
        doNothing().when(service).savePassword(anyString(), any(PasswordDto.class));

        mockMvc.perform(patch(EndpointUrlAndConstants.SAVE_PASSWORD)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(service, times(1)).savePassword(anyString(), any(PasswordDto.class));
    }

    @Test
    public void savePasswordWithoutAuthorization() throws Exception {
        PasswordDto passwordDto = requestDtoBuilder.createPasswordDto(EndpointUrlAndConstants.PASSWORD);
        String json = convertRequestDtoToJson(passwordDto);

        mockMvc.perform(patch(EndpointUrlAndConstants.SAVE_PASSWORD)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "SELF_REGISTRATION")
    public void saveResidentPassportWithAuthorization() throws Exception {
        PassportNumberDto passportNumberDto = requestDtoBuilder
                .createPassportNumberDto(EndpointUrlAndConstants.PASSPORT_NUMBER);
        String json = convertRequestDtoToJson(passportNumberDto);

        doNothing().when(clientUtil).checkIfTokenClientIdMatchClientId(anyString());
        doNothing().when(service).saveResidentPassportNumber(anyString(), any(PassportNumberDto.class));

        mockMvc.perform(patch(EndpointUrlAndConstants.SAVE_PASSPORT_NUMBER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(clientUtil, times(1)).checkIfTokenClientIdMatchClientId(anyString());
        verify(service, times(1)).saveResidentPassportNumber(anyString(), any(PassportNumberDto.class));
    }

    @Test
    public void saveResidentPassportWithoutAuthorization() throws Exception {
        PassportNumberDto passportNumberDto = requestDtoBuilder
                .createPassportNumberDto(EndpointUrlAndConstants.PASSPORT_NUMBER);
        String json = convertRequestDtoToJson(passportNumberDto);

        mockMvc.perform(patch(EndpointUrlAndConstants.SAVE_PASSPORT_NUMBER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "SELF_REGISTRATION")
    public void saveResidentFullNameWithAuthorization() throws Exception {
        FullNameDto fullNameDto = requestDtoBuilder.createFullNameDto();
        String json = convertRequestDtoToJson(fullNameDto);

        doNothing().when(clientUtil).checkIfTokenClientIdMatchClientId(anyString());
        doNothing().when(service).saveResidentFullName(anyString(), any(FullNameDto.class));

        mockMvc.perform(patch(EndpointUrlAndConstants.SAVE_FULL_NAME)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(clientUtil, times(1)).checkIfTokenClientIdMatchClientId(anyString());
        verify(service, times(1)).saveResidentFullName(anyString(), any(FullNameDto.class));
    }

    @Test
    public void saveResidentFullNameWithoutAuthorization() throws Exception {
        FullNameDto fullNameDto = requestDtoBuilder.createFullNameDto();
        String json = convertRequestDtoToJson(fullNameDto);

        mockMvc.perform(patch(EndpointUrlAndConstants.SAVE_FULL_NAME)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "SELF_REGISTRATION")
    public void saveSecurityQuestionAnswerWithAuthorization() throws Exception {
        SecurityQuestionAnswerDto questionAnswerDto = requestDtoBuilder.createSecurityQuestionAnswerDto();
        String json = convertRequestDtoToJson(questionAnswerDto);

        doNothing().when(clientUtil).checkIfTokenClientIdMatchClientId(anyString());
        doNothing().when(service).saveSecurityQuestionAnswer(anyString(), any(SecurityQuestionAnswerDto.class));

        mockMvc.perform(patch(EndpointUrlAndConstants.SAVE_SECURITY_QUESTION_ANSWER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(clientUtil, times(1)).checkIfTokenClientIdMatchClientId(anyString());
        verify(service, times(1)).saveSecurityQuestionAnswer(anyString(), any(SecurityQuestionAnswerDto.class));
    }

    @Test
    public void saveSecurityQuestionAnswerWithoutAuthorization() throws Exception {
        SecurityQuestionAnswerDto questionAnswerDto = requestDtoBuilder.createSecurityQuestionAnswerDto();
        String json = convertRequestDtoToJson(questionAnswerDto);

        mockMvc.perform(patch(EndpointUrlAndConstants.SAVE_SECURITY_QUESTION_ANSWER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "REGISTRATION")
    public void acceptRBSSRulesWithAuthorization() throws Exception {
        AcceptRBSSRulesDto acceptRBSSRulesDto = requestDtoBuilder.createAcceptRBSSRulesDto(true);
        String json = convertRequestDtoToJson(acceptRBSSRulesDto);

        doNothing().when(clientUtil).checkIfTokenClientIdMatchClientId(anyString());
        doNothing().when(service).acceptRBSSRules(anyString());

        mockMvc.perform(patch(EndpointUrlAndConstants.ACCEPT_RBSS_RULES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(clientUtil, times(1)).checkIfTokenClientIdMatchClientId(anyString());
        verify(service, times(1)).acceptRBSSRules(anyString());
    }

    @Test
    public void acceptRBSSRulesWithoutAuthorization() throws Exception {
        AcceptRBSSRulesDto acceptRBSSRulesDto = requestDtoBuilder.createAcceptRBSSRulesDto(true);
        String json = convertRequestDtoToJson(acceptRBSSRulesDto);

        mockMvc.perform(patch(EndpointUrlAndConstants.ACCEPT_RBSS_RULES)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    private <T> String convertRequestDtoToJson(T requestDto) {
        Gson gson = new Gson();
        return gson.toJson(requestDto);
    }
}
