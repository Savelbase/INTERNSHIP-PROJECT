package com.rmn.toolkit.bankinfoapplication.dto.response.success;

import com.rmn.toolkit.bankinfoapplication.model.Service;
import com.rmn.toolkit.bankinfoapplication.model.type.DepartmentType;
import com.rmn.toolkit.bankinfoapplication.model.type.ScheduleType;
import com.rmn.toolkit.bankinfoapplication.model.type.StatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDto {
    private String id;
    private String address;
    private String city;
    private String coordinates;
    private String name;
    private List<ScheduleDto> schedule;
    private StatusType status;
    private DepartmentType type;
    private Set<Service> services;
    private String zoneId;
    private ScheduleType scheduleType;
}
