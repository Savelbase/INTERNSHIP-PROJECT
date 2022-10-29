package com.rmn.toolkit.bankinfoapplication.controller;

import com.rmn.toolkit.bankinfoapplication.advice.GlobalExceptionHandler;
import com.rmn.toolkit.bankinfoapplication.model.type.DepartmentType;
import com.rmn.toolkit.bankinfoapplication.model.type.ScheduleType;
import com.rmn.toolkit.bankinfoapplication.service.DepartmentService;
import com.rmn.toolkit.bankinfoapplication.util.DepartmentUtil;
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
        DepartmentController.class,
        DepartmentService.class
}, properties = "spring.cloud.config.enabled=false")
@MockBeans({
        @MockBean(DepartmentService.class),
        @MockBean(DepartmentUtil.class)
})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DepartmentControllerUnitTests {
    private final MockMvc mockMvc;
    private final DepartmentService service;

    @Test
    @SneakyThrows
    public void getDepartmentsInformation()  {
        when(service.findAll(anyList(), any(DepartmentType.class), any(ScheduleType.class)))
                .thenReturn(EndpointUrlAndConstants.DEPARTMENT_DTO_LIST);

        mockMvc.perform(get(EndpointUrlAndConstants.GET_DEPARTMENTS_INFO)
                        .param("services", "")
                        .param("departmentType", String.valueOf(DepartmentType.DEPARTMENT))
                        .param("scheduleType", String.valueOf(ScheduleType.OPEN)))
                .andExpect(status().isOk());

        verify(service, times(1)).findAll(anyList(), any(DepartmentType.class), any(ScheduleType.class));
    }
    @Test
    @SneakyThrows
    public void getDepartmentById()  {
        when(service.findById(anyString())).thenReturn(EndpointUrlAndConstants.GET_DEPARTMENT);

        mockMvc.perform(get(EndpointUrlAndConstants.GET_DEPARTMENT_INFO_BY_ID))
                .andExpect(status().isOk());

        verify(service, times(1)).findById(anyString());
    }
}
