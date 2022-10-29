package com.rmn.toolkit.bankinfoapplication.service;

import com.rmn.toolkit.bankinfoapplication.dto.response.success.DepartmentDto;
import com.rmn.toolkit.bankinfoapplication.model.Department;
import com.rmn.toolkit.bankinfoapplication.model.type.DepartmentType;
import com.rmn.toolkit.bankinfoapplication.model.type.ScheduleType;
import com.rmn.toolkit.bankinfoapplication.model.type.ServiceName;
import com.rmn.toolkit.bankinfoapplication.repository.DepartmentRepository;
import com.rmn.toolkit.bankinfoapplication.repository.DepartmentRepositoryImpl;
import com.rmn.toolkit.bankinfoapplication.util.DepartmentUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Data
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentRepositoryImpl departmentRepositoryImpl;
    private final DepartmentUtil departmentUtil;

    public DepartmentDto findById(String id) {
        Department department = departmentUtil.findById(id);
        departmentUtil.changeDepartmentStatus(department);
        return departmentUtil.createDepartmentDto(department);
    }

    public List<DepartmentDto> findAll(List<ServiceName> services, DepartmentType departmentType, ScheduleType graphicType) {
        List<Department> departments = departmentRepositoryImpl
                .findAllDepartmentsByPredicates(services, departmentType, graphicType);
        departmentUtil.changeDepartmentStatus(departments);
        departments = departmentUtil.filterByStatusType(departments, graphicType);
        return departmentUtil.createListDepartmentDto(departments);
    }
}
