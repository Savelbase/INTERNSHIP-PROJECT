package com.rmn.toolkit.cards.query.model;

import com.rmn.toolkit.cards.query.model.type.ClientStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    @Id
    @NotNull
    private String id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ClientStatusType status;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<CardRequisites> cardRequisites;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Card> cards;
}
