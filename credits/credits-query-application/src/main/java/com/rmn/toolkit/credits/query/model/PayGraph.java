package com.rmn.toolkit.credits.query.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "pay_graph")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayGraph {
    @Id
    @NotNull
    private String id;

    @NotNull
    private String creditId;

    @Type(type = "jsonb")
    private List<Payment> paymentList;

    @OneToOne(mappedBy = "payGraph", fetch = FetchType.LAZY)
    private Credit credit;
}
