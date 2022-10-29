package com.rmn.toolkit.user.registration.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
    private String mobilePhone;

    private String passportNumber;
    private boolean resident;
    private String firstName;
    private String lastName;
    private String middleName;
    private boolean bankClient;
    private String password;
    private String securityQuestion;
    private String securityAnswer;
    private boolean registered;

    @NotNull
    private String roleId;

    private String verificationCodeId;
    private ZonedDateTime accessionDateTime;

    @NotNull
    private Integer version;
}
