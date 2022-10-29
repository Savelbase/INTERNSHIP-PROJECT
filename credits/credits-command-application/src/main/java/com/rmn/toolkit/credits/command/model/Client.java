package com.rmn.toolkit.credits.command.model;

import com.rmn.toolkit.credits.command.model.iface.Versionable;
import com.rmn.toolkit.credits.command.model.type.ClientStatusType;
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

    @NotNull
    @Enumerated(EnumType.STRING)
    private ClientStatusType status;

    private Integer version;
}
