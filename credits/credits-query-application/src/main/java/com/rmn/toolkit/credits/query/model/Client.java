package com.rmn.toolkit.credits.query.model;

import com.rmn.toolkit.credits.query.model.type.ClientStatusType;
import com.rmn.toolkit.credits.query.model.iface.Versionable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client implements Versionable {
    @Id
    @NotNull
    private String id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ClientStatusType status;

    @NotNull
    private Integer version;
}

