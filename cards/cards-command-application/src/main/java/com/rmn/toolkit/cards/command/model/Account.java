package com.rmn.toolkit.cards.command.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
}
