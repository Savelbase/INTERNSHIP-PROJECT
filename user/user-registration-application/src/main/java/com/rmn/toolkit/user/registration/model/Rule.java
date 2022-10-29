package com.rmn.toolkit.user.registration.model;

import com.rmn.toolkit.user.registration.model.type.RuleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "rules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rule {
    @Id
    @NotNull
    private String id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RuleType ruleType;

    @NotNull
    private String text;

    private boolean actual;
}
