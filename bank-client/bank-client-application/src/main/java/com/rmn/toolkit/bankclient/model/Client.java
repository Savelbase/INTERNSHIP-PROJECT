package com.rmn.toolkit.bankclient.model;

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
    private String mobilePhone;

    @NotNull
    private String passportNumber;

    @NotNull
    private boolean resident;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String middleName;

    @NotNull
    private String securityQuestion;

    @NotNull
    private String securityAnswer;

    @NotNull
    private boolean bankClient;

    @NotNull
    private Integer version;
}
