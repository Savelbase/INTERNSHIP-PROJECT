package com.rmn.toolkit.cards.query.dto.response.success;

import com.rmn.toolkit.cards.query.model.type.CardStatusType;
import com.rmn.toolkit.cards.query.model.type.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDto {
    private String id;
    private String cardName;
    private CardStatusType status;
    private CardType type;
}
