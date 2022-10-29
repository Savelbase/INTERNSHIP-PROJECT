package com.rmn.toolkit.cards.query.service;

import com.rmn.toolkit.cards.query.dto.request.CardReceiptsFilters;
import com.rmn.toolkit.cards.query.dto.request.CardStatementsPeriodDto;
import com.rmn.toolkit.cards.query.dto.request.TransactionTypeDto;
import com.rmn.toolkit.cards.query.dto.response.success.ReceiptDto;
import com.rmn.toolkit.cards.query.model.projection.ReceiptView;
import com.rmn.toolkit.cards.query.model.type.TransactionType;
import com.rmn.toolkit.cards.query.repository.ReceiptsRepository;
import com.rmn.toolkit.cards.query.util.ReceiptsUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReceiptsService {
    private static final String ALL_TRANSACTION_DESCRIPTION_TYPE_EN = "all";
    private static final String ALL_TRANSACTION_DESCRIPTION_TYPE_RUS = "все операции";

    private final ReceiptsRepository receiptsRepository;
    private final ReceiptsUtil receiptsUtil;

    @Transactional(readOnly = true)
    public List<ReceiptDto> getCardStatements(CardStatementsPeriodDto cardStatementsPeriodDto, int page, int size) {
        ZonedDateTime startPeriod = ZonedDateTime.of(cardStatementsPeriodDto.getStartPeriod(), LocalTime.MIN,
                ZonedDateTime.now().getZone());
        ZonedDateTime endPeriod = ZonedDateTime.of(cardStatementsPeriodDto.getEndPeriod(), LocalTime.MIN,
                ZonedDateTime.now().getZone());

        Pageable pageable = PageRequest.of(page, size);
        List<ReceiptView> receiptsView = receiptsRepository.findAllByCardIdAndTransactionTimeBetween(
                cardStatementsPeriodDto.getCardId(), startPeriod, endPeriod, pageable);

        return receiptsUtil.createListReceiptDto(receiptsView);
    }

    @Transactional(readOnly = true)
    public List<ReceiptDto> getCardStatementsWithoutPagination(String cardId, ZonedDateTime startPeriod, ZonedDateTime endPeriod) {
        List<ReceiptView> receiptsView = receiptsRepository.
                findAllByCardIdAndTransactionTimeBetween(cardId, startPeriod, endPeriod);
        return receiptsUtil.createListReceiptDto(receiptsView);
    }

    @Transactional(readOnly = true)
    public List<ReceiptDto> getReceipts(CardReceiptsFilters filters, int page, int size) {
        Pair<BigDecimal, BigDecimal> operationSumPairs = receiptsUtil.parseFiltersToOperationSums(filters);
        BigDecimal minOperationSum = operationSumPairs.getLeft();
        BigDecimal maxOperationSum = operationSumPairs.getRight();

        Pair<ZonedDateTime, ZonedDateTime> periodPairs = receiptsUtil.parseFiltersToDateTimePeriods(filters);
        ZonedDateTime startPeriod = periodPairs.getLeft();
        ZonedDateTime endPeriod = periodPairs.getRight();

        String cardId = filters.getCardId();
        TransactionType transactionType = filters.getTransactionType();
        String transactionTypeValue = String.valueOf(transactionType);

        Pageable pageable = PageRequest.of(page, size);
        List<ReceiptView> receiptsView;
        if (TransactionType.ALL.equals(transactionType)) {
            receiptsView = receiptsRepository.findAllByCardIdAndTransactionAmountBetweenAndTransactionTimeBetween(
                    cardId, minOperationSum, maxOperationSum, startPeriod, endPeriod, pageable);
        } else {
            receiptsView = receiptsRepository
                    .findAllByCardIdAndTransactionAmountBetweenAndTransactionTimeBetweenAndTransactionType(cardId,
                            minOperationSum, maxOperationSum, startPeriod, endPeriod, transactionTypeValue, pageable);
        }
        return receiptsUtil.createListReceiptDto(receiptsView);
    }

    @Transactional(readOnly = true)
    public List<ReceiptDto> searchAllReceiptsByTransactionType(TransactionTypeDto transactionTypeDto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<ReceiptView> receiptsView;
        String cardId = transactionTypeDto.getCardId();
        String transactionDescription = transactionTypeDto.getTransactionType();

        if (ALL_TRANSACTION_DESCRIPTION_TYPE_EN.equalsIgnoreCase(transactionDescription) ||
                ALL_TRANSACTION_DESCRIPTION_TYPE_RUS.equalsIgnoreCase(transactionDescription)) {
            receiptsView = receiptsRepository.findAllByCard_Id(cardId, pageable);
        } else {
            receiptsView = receiptsRepository
                    .findAllByCard_IdAndTransactionTypeContainsIgnoreCase(cardId, transactionDescription, pageable);
        }
        return receiptsUtil.createListReceiptDto(receiptsView);
    }
}
