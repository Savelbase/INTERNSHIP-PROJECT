package com.rmn.toolkit.cards.query.dto.response.success;

import com.rmn.toolkit.cards.query.model.type.CardOrderStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardOrderDto {
    private String id;
    private String clientId;
    private String cardProductId;
    private CardOrderStatusType status;
}
