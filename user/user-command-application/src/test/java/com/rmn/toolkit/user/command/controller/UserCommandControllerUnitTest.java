package com.rmn.toolkit.user.command.controller;

import com.google.gson.Gson;
import com.rmn.toolkit.user.command.advice.GlobalExceptionHandler;
import com.rmn.toolkit.user.command.dto.request.ApprovedBankClientDto;
import com.rmn.toolkit.user.command.model.Client;
import com.rmn.toolkit.user.command.security.SecurityConfig;
import com.rmn.toolkit.user.command.security.SecurityUtil;
import com.rmn.toolkit.user.command.security.jwt.JwtUserDetailsService;
import com.rmn.toolkit.user.command.security.jwt.JwtUtil;
import com.rmn.toolkit.user.command.service.UserCommandService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureWebMvc
@SpringBootTest(classes = {
        SecurityConfig.class,
        JwtUserDetailsService.class,
        JwtUtil.class,
        GlobalExceptionHandler.class,
        ResponseUtil.class,
        UserCommandController.class,
        UserCommandService.class,
        ClientUtil.class,
        SecurityUtil.class,
        RequestDtoBuilder.class
}, properties = "spring.cloud.config.enabled=false")
@MockBeans({
        @MockBean(UserCommandService.class),
        @MockBean(ResponseUtil.class),
        @MockBean(ClientUtil.class),
        @MockBean(SecurityUtil.class)
})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserCommandControllerUnitTest {
    private final MockMvc mockMvc;
    private final RequestDtoBuilder requestDtoBuilder;
    private final UserCommandService service;
    private final ClientUtil clientUtil;
    private final SecurityUtil securityUtil;

    @Test
    @WithMockUser(authorities = "APPROVE_BANK_CLIENT")
    public void approveBankClientWithAuthorization() throws Exception {
        ApprovedBankClientDto approvedBankClientDto = requestDtoBuilder.createApprovedBankClientDto();
        String json = convertRequestDtoToJson(approvedBankClientDto);
        Client client = requestDtoBuilder.createClient();

        when(securityUtil.getCurrentUserId()).thenReturn(EndpointUrlAndConstants.TEST_VALUE);
        doNothing().when(clientUtil).checkIfClientIsBlocked(anyString());
        when(clientUtil.findClientById(anyString())).thenReturn(client);
        doNothing().when(service).approveBankClient(any(Client.class), anyString());

        mockMvc.perform(patch(EndpointUrlAndConstants.APPROVE_BANK_CLIENT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(securityUtil, times(1)).getCurrentUserId();
        verify(clientUtil, times(1)).checkIfClientIsBlocked(anyString());
        verify(clientUtil, times(1)).findClientById(anyString());
        verify(service, times(1)).approveBankClient(any(Client.class), anyString());
    }

    @Test
    public void approveBankClientWithoutAuthorization() throws Exception {
        ApprovedBankClientDto approvedBankClientDto = requestDtoBuilder.createApprovedBankClientDto();
        String json = convertRequestDtoToJson(approvedBankClientDto);

        mockMvc.perform(patch(EndpointUrlAndConstants.APPROVE_BANK_CLIENT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "DELETE_USER")
    public void deleteUserByIdWithAuthorization() throws Exception {
        when(securityUtil.getCurrentUserId()).thenReturn(EndpointUrlAndConstants.TEST_VALUE);
        doNothing().when(service).deleteUserById(anyString(), anyString());

        mockMvc.perform(delete(EndpointUrlAndConstants.DELETE_USER))
                .andExpect(status().isOk());

        verify(securityUtil, times(1)).getCurrentUserId();
        verify(service, times(1)).deleteUserById(anyString(), anyString());
    }

    @Test
    public void deleteUserByIdWithoutAuthorization() throws Exception {
        mockMvc.perform(delete(EndpointUrlAndConstants.DELETE_USER))
                .andExpect(status().isForbidden());
    }

    private <T> String convertRequestDtoToJson(T requestDto) {
        Gson gson = new Gson();
        return gson.toJson(requestDto);
    }
}
