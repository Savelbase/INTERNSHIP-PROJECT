package com.rmn.toolkit.cards.query.repository;

import com.rmn.toolkit.cards.query.model.Receipt;
import com.rmn.toolkit.cards.query.model.projection.ReceiptView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReceiptsRepository extends JpaRepository<Receipt, String> {
    Optional<ReceiptView> findProjectionById(String id);
    List<ReceiptView> findAllByCard_Id(String cardId, Pageable pageable);
    List<ReceiptView> findAllByCard_IdAndTransactionTypeContainsIgnoreCase(String cardId, String transactionType,
                                                                           Pageable pageable);
    List<ReceiptView> findAllByCardIdAndTransactionTimeBetween(String cardId, ZonedDateTime startPeriod,
                                                               ZonedDateTime endPeriod, Pageable pageable);
    List<ReceiptView> findAllByCardIdAndTransactionTimeBetween(String cardId, ZonedDateTime startPeriod,
                                                               ZonedDateTime endPeriod);
    List<ReceiptView>
    findAllByCardIdAndTransactionAmountBetweenAndTransactionTimeBetween(String cardId, BigDecimal minSum, BigDecimal maxSum,
                                                                        ZonedDateTime startPeriod, ZonedDateTime endPeriod,
                                                                        Pageable pageable);
    List<ReceiptView>
    findAllByCardIdAndTransactionAmountBetweenAndTransactionTimeBetweenAndTransactionType(String cardId, BigDecimal minSum,
                                                                                          BigDecimal maxSum,
                                                                                          ZonedDateTime startPeriod,
                                                                                          ZonedDateTime endPeriod,
                                                                                          String transactionType,
                                                                                          Pageable pageable);
}
