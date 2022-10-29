package com.rmn.toolkit.bankinfoapplication.model;


import com.rmn.toolkit.bankinfoapplication.model.type.ServiceName;
import com.rmn.toolkit.bankinfoapplication.model.type.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Service implements Serializable {
    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    private ServiceName name ;
    @Enumerated(EnumType.STRING)
    private ServiceType type ;
}
