package com.rmn.toolkit.cards.query.model;

import com.rmn.toolkit.cards.query.model.type.AgreementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "agreement")
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
