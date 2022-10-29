package com.rmn.toolkit.user.command.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "passport_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
