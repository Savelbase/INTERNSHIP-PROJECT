package com.rmn.toolkit.cards.command.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
}
