package com.rmn.toolkit.cards.query.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "card_conditions_dictionary")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class CardConditionsDictionary {
    @Id
    private String id ;
    private String service ;
    private String partnerCashBack ;
    @Type(type = "jsonb")
    private List<String> cashBackList;
    private String maxCashBackSum ;
    @Type(type = "jsonb")
    private List<String> withdrawConditions ;
    private String moneyTransferByPhoneNumber ;

}
