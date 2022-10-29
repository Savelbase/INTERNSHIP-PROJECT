package com.rmn.toolkit.authorization.model;

import com.rmn.toolkit.authorization.model.type.UserStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Versionable {
    @Id
    @NotNull
    private String id;

    @NotNull
    private String mobilePhone;

    @NotNull
    private String password;

    private String pinCode;

    @NotNull
    private String roleId;

    @NotNull
    private Integer attemptCounter;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserStatusType status;

    @NotNull
    private Integer version;
}
