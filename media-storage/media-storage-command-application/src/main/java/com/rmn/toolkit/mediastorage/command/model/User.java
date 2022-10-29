package com.rmn.toolkit.mediastorage.command.model;

import com.rmn.toolkit.mediastorage.command.model.type.UserStatusType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Versionable {
    @Id
    @NotNull
    private String id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserStatusType status;

    @NotNull
    private Integer version;
}
