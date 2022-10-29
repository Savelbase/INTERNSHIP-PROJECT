package com.rmn.toolkit.bankinfoapplication.util;

import com.rmn.toolkit.bankinfoapplication.dto.response.success.DepartmentDto;
import com.rmn.toolkit.bankinfoapplication.dto.response.success.ScheduleDto;
import com.rmn.toolkit.bankinfoapplication.exception.notfound.DepartmentNotFoundException;
import com.rmn.toolkit.bankinfoapplication.model.Department;
import com.rmn.toolkit.bankinfoapplication.model.Schedule;
import com.rmn.toolkit.bankinfoapplication.model.type.ScheduleType;
import com.rmn.toolkit.bankinfoapplication.model.type.StatusType;
import com.rmn.toolkit.bankinfoapplication.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class DepartmentUtil {
    private final DepartmentRepository departmentRepository;

    public void changeDepartmentStatus(List<Department> departments) {
        departments.forEach(this::changeDepartmentStatus);
    }

    public void changeDepartmentStatus(Department department) {
        String dayOfWeek = LocalDate.now().getDayOfWeek().name();
        department.getSchedule().stream().filter(day -> day.getDay().name().equalsIgnoreCase(dayOfWeek))
                .peek(schedule -> {
                    if (schedule.getTo().isBefore(ZonedDateTime.now(ZoneId.of(department.getZoneId())).toLocalTime()) ||
                            schedule.getFrom().isAfter(ZonedDateTime.now(ZoneId.of(department.getZoneId())).toLocalTime())) {
                        department.setStatus(StatusType.CLOSED);
                    }
                })
                .findFirst();
    }

    public Department findById(String id) {
        return departmentRepository.findById(id).orElseThrow(() -> {
            throw new DepartmentNotFoundException(id);
        });
    }

    public List<Department> filterByStatusType(List<Department> departments, ScheduleType scheduleType) {
        if (scheduleType != null && scheduleType.equals(ScheduleType.OPEN)) {
            return departments.stream().filter(department -> department.getStatus().equals(StatusType.OPEN))
                    .collect(Collectors.toList());
        }
        return departments;
    }

    public DepartmentDto createDepartmentDto(Department department) {
        return DepartmentDto.builder()
                .id(department.getId())
                .address(department.getAddress())
                .city(department.getCity())
                .coordinates(department.getCoordinates())
                .name(department.getName())
                .schedule(createListScheduleDto(department.getSchedule()))
                .status(department.getStatus())
                .type(department.getType())
                .services(department.getServices())
                .zoneId(department.getZoneId())
                .scheduleType(department.getScheduleType())
                .build();
    }

    public List<DepartmentDto> createListDepartmentDto(List<Department> departments) {
        return departments.stream().map(this::createDepartmentDto).toList();
    }

    public ScheduleDto createScheduleDto(Schedule schedule) {
        return ScheduleDto.builder()
                .day(schedule.getDay())
                .from(String.valueOf(schedule.getFrom()))
                .to(String.valueOf(schedule.getTo()))
                .build();
    }

    public List<ScheduleDto> createListScheduleDto(List<Schedule> schedule) {
        return schedule.stream().map(this::createScheduleDto).toList();
    }
}
