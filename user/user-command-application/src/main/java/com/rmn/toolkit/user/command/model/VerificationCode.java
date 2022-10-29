package com.rmn.toolkit.user.command.model;

import com.rmn.toolkit.user.command.model.iface.Versionable;
import com.rmn.toolkit.user.command.model.type.VerificationCodeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private VerificationCodeType appointment;

    @NotNull
    private Integer version;
}
