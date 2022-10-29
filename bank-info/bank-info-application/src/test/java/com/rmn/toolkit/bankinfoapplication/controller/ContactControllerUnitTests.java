package com.rmn.toolkit.bankinfoapplication.controller;

import com.rmn.toolkit.bankinfoapplication.advice.GlobalExceptionHandler;
import com.rmn.toolkit.bankinfoapplication.service.ContactService;
import com.rmn.toolkit.bankinfoapplication.util.EndpointUrlAndConstants;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureWebMvc
@SpringBootTest(classes = {
        GlobalExceptionHandler.class,
        ContactController.class,
        ContactService.class
}, properties = "spring.cloud.config.enabled=false")
@MockBeans(@MockBean(ContactService.class))
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContactControllerUnitTests {
    private final MockMvc mockMvc;
    private final ContactService service;

    @Test
    @SneakyThrows
    public void getDepartmentsInformation()  {
        when(service.getContacts()).thenReturn(EndpointUrlAndConstants.CONTACT_LIST);

        mockMvc.perform(get(EndpointUrlAndConstants.GET_CONTACTS_INFO))
                .andExpect(status().isOk());

        verify(service, times(1)).getContacts();
    }
}
