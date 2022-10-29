package com.rmn.toolkit.bankinfoapplication.model;

import com.rmn.toolkit.bankinfoapplication.model.type.Day;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Schedule implements Serializable {
    private Day day ;
    private LocalTime from ;
    private LocalTime to ;
}
