package com.rmn.toolkit.bankinfoapplication.model;

import com.rmn.toolkit.bankinfoapplication.model.type.DepartmentType;
import com.rmn.toolkit.bankinfoapplication.model.type.ScheduleType;
import com.rmn.toolkit.bankinfoapplication.model.type.StatusType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Department implements Serializable {
    @Id
    private String id ;
    private String address ;
    private String city ;
    private String coordinates ;
    private String name ;
    @Type(type = "jsonb")
    @Column(name = "schedule", columnDefinition = "jsonb")
    private List<Schedule> schedule ;
    @Enumerated(EnumType.STRING)
    private StatusType status ;
    @Enumerated(EnumType.STRING)
    private DepartmentType type ;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "department_service",
            joinColumns = { @JoinColumn(name = "department_id") },
            inverseJoinColumns = { @JoinColumn(name = "service_id") }
    )
    private Set<Service> services ;
    private String zoneId;
    @Enumerated(EnumType.STRING)
    private ScheduleType scheduleType;
}
