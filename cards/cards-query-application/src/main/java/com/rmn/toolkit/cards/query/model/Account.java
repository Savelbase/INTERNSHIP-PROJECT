package com.rmn.toolkit.cards.query.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Account {
    @Id
    @NotNull
    private String id;

    @NotNull
    private String clientId;

    @NotNull
    private String accountNumber;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    private CardRequisites cardRequisites;
}
