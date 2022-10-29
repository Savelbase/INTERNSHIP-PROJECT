package com.rmn.toolkit.cards.query.model;

import com.rmn.toolkit.cards.query.model.cardCondition.BankService;
import com.rmn.toolkit.cards.query.model.cardCondition.CashBack;
import com.rmn.toolkit.cards.query.model.cardCondition.InterestСonditions;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


@Entity
@Data
@Table(name = "card_conditions")
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Builder
public class CardConditions implements Serializable {
    @Id
    private String id ;
    @Type(type = "jsonb")
    private BankService service ;
    private Integer partnerCashBack ;
    @Type(type = "jsonb")
    private List<CashBack> cashBackList;
    private BigDecimal maxCashBackSum ;
    @Type(type = "jsonb")
    private List<InterestСonditions> withdrawConditions ;
    @Type(type = "jsonb")
    private InterestСonditions moneyTransferByPhoneNumber ;

}
