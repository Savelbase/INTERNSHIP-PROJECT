package com.rmn.toolkit.user.command.model;

import com.rmn.toolkit.user.command.model.iface.Versionable;
import com.rmn.toolkit.user.command.model.type.ClientStatusType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

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
    private String firstName;
    @NotNull
    private String lastName;
    private String middleName;
    @NotNull
    private ZonedDateTime accessionDateTime;
    @NotNull
    private String passportNumber;
    @NotNull
    private String roleId;
    @Enumerated(EnumType.STRING)
    @NotNull
    private ClientStatusType status;
    @NotNull
    private String mobilePhone;
    @NotNull
    private boolean resident;
    @NotNull
    private boolean bankClient;
    @NotNull
    private Integer version;
}