package com.rmn.toolkit.user.query.model;

import com.rmn.toolkit.user.query.model.iface.Versionable;
import com.rmn.toolkit.user.query.model.type.ClientStatusType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
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
    private String passportNumber;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ClientStatusType status;
    @NotNull
    private String mobilePhone;
    @NotNull
    private boolean resident;
    @NotNull
    private boolean bankClient;
    @NotNull
    private String roleId;
    @NotNull
    private ZonedDateTime accessionDateTime;
    @NotNull
    private Integer version;
    @OneToOne
    @JoinColumn(referencedColumnName = "id" , name = "id")
    private User user;
}
