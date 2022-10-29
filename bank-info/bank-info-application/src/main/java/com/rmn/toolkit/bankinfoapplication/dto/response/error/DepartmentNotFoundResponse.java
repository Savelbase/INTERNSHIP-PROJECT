package com.rmn.toolkit.bankinfoapplication.dto.response.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepartmentNotFoundResponse {
    @Schema(example = "2022-04-16T23:07:59.441143700Z")
    private Instant dateTime;
    private String message ;

    public DepartmentNotFoundResponse(String message){
        dateTime = Instant.now();
        this.message = message;
    }
}
