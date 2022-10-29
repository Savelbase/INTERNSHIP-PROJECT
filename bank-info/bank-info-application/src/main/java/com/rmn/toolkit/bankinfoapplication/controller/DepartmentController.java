package com.rmn.toolkit.bankinfoapplication.controller;

import com.rmn.toolkit.bankinfoapplication.dto.response.error.DepartmentNotFoundResponse;
import com.rmn.toolkit.bankinfoapplication.dto.response.success.DepartmentDto;
import com.rmn.toolkit.bankinfoapplication.model.type.DepartmentType;
import com.rmn.toolkit.bankinfoapplication.model.type.ScheduleType;
import com.rmn.toolkit.bankinfoapplication.model.type.ServiceName;
import com.rmn.toolkit.bankinfoapplication.service.DepartmentService;
import com.rmn.toolkit.bankinfoapplication.util.DocumentationUtil;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
@Tag(name = "Departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Departments info received successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DepartmentDto.class))))
    })
    public List<DepartmentDto> getInformationAboutDepartments(@RequestParam (required = false) List<ServiceName> services,
                                                              @RequestParam (required = false) DepartmentType departmentType,
                                                              @RequestParam (required = false) ScheduleType scheduleType) {
        return departmentService.findAll(services, departmentType, scheduleType);
    }

    @GetMapping("/{departmentId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Departments info received successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DepartmentDto.class)))),
            @ApiResponse(responseCode = "404", description = "Departments not found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DepartmentNotFoundResponse.class)),
                            examples = @ExampleObject(DocumentationUtil.DEPARTMENT_NOT_FOUND)))
    })
    public DepartmentDto getInformationAboutDepartments(@PathVariable String departmentId) {
        return departmentService.findById(departmentId);
    }
}
