package com.rmn.toolkit.cards.query.dto.response.success;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiptDto {
    private String id;
    private String transactionType;
    private ZonedDateTime dateTime;
    private String sumWithCurrency;
    private String recipientName;
    private String recipientAccountNumber;
    private String transactionLocation;
    private String additionalInfo;
}
