package com.rmn.toolkit.credits.command.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "credit_dictionary")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditDictionary {
    @Id
    @NotNull
    private String id;

    @NotNull
    private String name;
}
