package com.rmn.toolkit.deposits.query.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @NotNull
    private String id;

    @NotNull
    private String clientId;

    @NotNull
    private String accountNumber;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Deposit> deposit;
}
