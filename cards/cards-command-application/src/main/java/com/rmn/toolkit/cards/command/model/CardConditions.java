package com.rmn.toolkit.cards.command.model;

import com.rmn.toolkit.cards.command.model.cardCondition.BankService;
import com.rmn.toolkit.cards.command.model.cardCondition.CashBack;
import com.rmn.toolkit.cards.command.model.cardCondition.InterestСonditions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Table(name = "card_conditions")
@AllArgsConstructor
@NoArgsConstructor
public class CardConditions {
    @Id
    private String id ;
    @Type(type = "jsonb")
    private BankService service ;
    private Integer partnerCashBack ;
    @Type(type = "jsonb")
    private List<CashBack> cashBackList;
    @Type(type = "jsonb")
    private BigDecimal maxCashBackSum ;
    @Type(type = "jsonb")
    private List<InterestСonditions> withdrawConditions ;
    @Type(type = "jsonb")
    private InterestСonditions moneyTransferByPhoneNumber ;
    @OneToOne
    @MapsId
    @JoinColumn(name = "card_product_id")
    private CardProduct cardProduct;
}
