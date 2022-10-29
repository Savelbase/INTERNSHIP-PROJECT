package com.rmn.toolkit.bankinfoapplication.util;

import com.rmn.toolkit.bankinfoapplication.dto.response.success.DepartmentDto;
import com.rmn.toolkit.bankinfoapplication.dto.response.success.ScheduleDto;
import com.rmn.toolkit.bankinfoapplication.model.Contact;
import com.rmn.toolkit.bankinfoapplication.model.type.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface EndpointUrlAndConstants {
    String DEPARTMENT_ID = "67e1988f-3c97-46ec-93f2-85659a465d6d";

    String GET_CONTACTS_INFO = "/api/v1/contacts";
    String GET_DEPARTMENTS_INFO = "/api/v1/departments";
    String GET_DEPARTMENT_INFO_BY_ID = String.format("%s/%s", GET_DEPARTMENTS_INFO, DEPARTMENT_ID);

    List<Contact> CONTACT_LIST = List.of();
    List<DepartmentDto> DEPARTMENT_DTO_LIST = List.of();
    DepartmentDto GET_DEPARTMENT = DepartmentDto.builder()
            .schedule(List.of(ScheduleDto.builder()
                    .from(String.valueOf(LocalTime.MIN))
                    .to(String.valueOf(LocalTime.MAX))
                    .day(Day.MONDAY)
                    .build()))
            .id(UUID.randomUUID().toString())
            .status(StatusType.OPEN)
            .address("Address")
            .name("19")
            .type(DepartmentType.DEPARTMENT)
            .services(Set.of(com.rmn.toolkit.bankinfoapplication.model.Service.builder()
                    .id(UUID.randomUUID().toString())
                    .name(ServiceName.WITHDRAW)
                    .type(ServiceType.PRIMARY)
                    .build(), com.rmn.toolkit.bankinfoapplication.model.Service.builder()
                    .id(UUID.randomUUID().toString())
                    .name(ServiceName.PAY)
                    .type(ServiceType.ADDITIONAL)
                    .build()))
            .build();
}
