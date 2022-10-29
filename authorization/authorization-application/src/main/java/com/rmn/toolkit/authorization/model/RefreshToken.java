package com.rmn.toolkit.authorization.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {
    @Id
    @NotNull
    private String id;

    @NotNull
    private String hash;

    @NotNull
    private Instant expiryDateTime;

    @NotNull
    private String userId;
}
