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
@Table(name = "verification_codes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerificationCode implements Versionable {
    @Id
    @NotNull
    private String id;

    @NotNull
    private String clientId;

    private String verificationCode;
    private ZonedDateTime expiryDateTime;
    private Integer attemptCounter;

    @NotNull
    private ZonedDateTime nextRequestDateTime;

    private boolean exceededMaxLimit;
    private boolean verified;

    @NotNull
    private Integer version;
}
