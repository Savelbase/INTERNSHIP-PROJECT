package com.rmn.toolkit.bankinfoapplication.dto.response.success;

import com.rmn.toolkit.bankinfoapplication.model.type.Day;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDto {
    private Day day;
    private String from;
    private String to;
}
