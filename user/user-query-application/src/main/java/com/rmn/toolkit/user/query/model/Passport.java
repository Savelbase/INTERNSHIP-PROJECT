package com.rmn.toolkit.user.query.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "passport_data")
public class Passport {
    @Id
    @NotNull
    private String id;
    @NotNull
    private LocalDate issuanceDate;
    @NotNull
    private LocalDate expiryDate;
    @NotNull
    private String nationality;
    @NotNull
    private LocalDate birthDate;
    @NotNull
    private String passportNumber;
}
