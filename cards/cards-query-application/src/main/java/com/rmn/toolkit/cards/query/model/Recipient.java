package com.rmn.toolkit.cards.query.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "recipients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipient {
    @Id
    @NotNull
    private String id;

    @NotNull
    private String name;

    @NotNull
    private String accountNumber;

    @OneToMany(mappedBy = "recipient", fetch = FetchType.LAZY)
    private List<Receipt> receipts;
}
