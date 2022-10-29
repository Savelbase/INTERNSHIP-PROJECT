package com.rmn.toolkit.deposits.query.model;

import com.rmn.toolkit.deposits.query.model.type.AgreementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "agreements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agreement {
    @Id
    @NotNull
    private String id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AgreementType agreementType;

    @NotNull
    private String agreementText;

    private boolean actual;
}
