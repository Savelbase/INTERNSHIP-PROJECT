package com.rmn.toolkit.cards.query.dto.response.success;

import com.rmn.toolkit.cards.query.model.type.Currency;
import lombok.*;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardRequisitesDto {
    private String id;
    private String cardNumber;
    private String cardName;
    private String cardHolderFirstName;
    private String cardHolderLastName;
    private ZonedDateTime endCardPeriod;
    private String accountNumber;
    private Currency currency;
}
