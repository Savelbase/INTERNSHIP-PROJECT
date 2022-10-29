package com.rmn.toolkit.mediastorage.query.model;

import com.rmn.toolkit.mediastorage.query.model.type.UserStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserStatusType status;

    @NotNull
    private Integer version;
}
