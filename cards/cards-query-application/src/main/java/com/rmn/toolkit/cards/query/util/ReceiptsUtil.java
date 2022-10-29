package com.rmn.toolkit.cards.query.util;

import com.rmn.toolkit.cards.query.dto.request.CardReceiptsFilters;
import com.rmn.toolkit.cards.query.dto.response.success.ReceiptDto;
import com.rmn.toolkit.cards.query.exception.notfound.CardReceiptsNotFoundException;
import com.rmn.toolkit.cards.query.model.projection.CardProductView;
import com.rmn.toolkit.cards.query.model.projection.ReceiptView;
import com.rmn.toolkit.cards.query.model.projection.RecipientView;
import com.rmn.toolkit.cards.query.model.type.PaymentOperationType;
import com.rmn.toolkit.cards.query.repository.ReceiptsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReceiptsUtil {
    private static final int DEFAULT_PERIOD_IN_DAYS = 10;

    private final ReceiptsRepository receiptsRepository;

    public ReceiptView findReceiptViewById(String id) {
        return receiptsRepository.findProjectionById(id)
                .orElseThrow(() -> {
                    log.error("Card receipts by id='{}' not found", id);
                    throw new CardReceiptsNotFoundException(id);
                });
    }

    public ReceiptDto createReceiptDto(ReceiptView receiptView) {
        CardProductView cardProductView = receiptView.getCard().getCardProduct();
        String sumWithCurrency = String.valueOf(receiptView.getTransactionAmount())
                .concat(" " + cardProductView.getCurrency());
        RecipientView recipientView = receiptView.getRecipient();
        return ReceiptDto.builder()
                .id(receiptView.getId())
                .transactionType(receiptView.getTransactionType())
                .dateTime(receiptView.getTransactionTime())
                .sumWithCurrency(sumWithCurrency)
                .recipientName(recipientView.getName())
                .recipientAccountNumber(recipientView.getAccountNumber())
                .transactionLocation(receiptView.getTransactionLocation())
                .additionalInfo(receiptView.getAdditionalInfo())
                .build();
    }

    public List<ReceiptDto> createListReceiptDto(List<ReceiptView> receiptsView) {
        return receiptsView.stream().map(this::createReceiptDto).toList();
    }

    public Pair<BigDecimal, BigDecimal> parseFiltersToOperationSums(CardReceiptsFilters filters) {
        BigDecimal minSum = filters.getMinOperationSum();
        BigDecimal maxSum = filters.getMaxOperationSum();
        PaymentOperationType operationType = filters.getOperationType();
        if (PaymentOperationType.EXPENSE.equals(operationType)) {
            minSum = filters.getMaxOperationSum().negate();
            maxSum = filters.getMinOperationSum().negate();
        } else if (PaymentOperationType.ALL.equals(operationType)) {
            minSum = filters.getMaxOperationSum().negate();
            maxSum = filters.getMaxOperationSum();
        }
        return Pair.of(minSum, maxSum);
    }

    public Pair<ZonedDateTime, ZonedDateTime> parseFiltersToDateTimePeriods(CardReceiptsFilters filters) {
        LocalDate ldStartPeriod = filters.getStartPeriod();
        LocalDate ldEndPeriod = filters.getEndPeriod();
        ZonedDateTime startPeriod;
        ZonedDateTime endPeriod;
        if (Objects.isNull(ldStartPeriod) || Objects.isNull(ldEndPeriod)) {
            startPeriod = ZonedDateTime.now();
            endPeriod = startPeriod.plusDays(DEFAULT_PERIOD_IN_DAYS);
        } else {
            startPeriod = ZonedDateTime.of(ldStartPeriod, LocalTime.MIN, ZonedDateTime.now().getZone());
            endPeriod = ZonedDateTime.of(ldEndPeriod, LocalTime.MIN, ZonedDateTime.now().getZone());
        }
        return Pair.of(startPeriod, endPeriod);
    }
}
