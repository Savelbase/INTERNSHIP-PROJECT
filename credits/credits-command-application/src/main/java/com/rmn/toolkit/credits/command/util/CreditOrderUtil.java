package com.rmn.toolkit.credits.command.util;

import com.rmn.toolkit.credits.command.dto.request.CreditOrderDto;
import com.rmn.toolkit.credits.command.exception.notfound.CreditOrderNotFoundException;
import com.rmn.toolkit.credits.command.model.CreditOrder;
import com.rmn.toolkit.credits.command.model.type.CreditOrderStatusType;
import com.rmn.toolkit.credits.command.repository.CreditOrderRepository;
import com.rmn.toolkit.cryptography.RSAUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreditOrderUtil {
    private static final int VERSION = 1;
    private final RSAUtils rsaUtils ;

    private final CreditOrderRepository creditOrderRepository;

    @SneakyThrows
    public CreditOrder createCreditOrder(CreditOrderDto creditOrderDto, String clientId) {
        return CreditOrder.builder()
                .id(UUID.randomUUID().toString())
                .creditAmount(creditOrderDto.getCreditAmount())
                .averageMonthlyIncome(creditOrderDto.getAverageMonthlyIncome())
                .averageMonthlyExpenses(creditOrderDto.getAverageMonthlyExpenses())
                .employerTin(rsaUtils.encryptedDataByPublicKey(creditOrderDto.getEmployerTin()))
                .clientId(clientId)
                .status(CreditOrderStatusType.PROCESSING)
                .startCreditPeriod(LocalDate.now())
                .endCreditPeriod(LocalDate.now().plusMonths(creditOrderDto.getMonthPeriod()))
                .creditProductName(creditOrderDto.getCreditProductName())
                .version(VERSION)
                .build();
    }

    public CreditOrder findCreditOrderById(String creditOrderId) {
        return creditOrderRepository.findById(creditOrderId)
                .orElseThrow(() -> {
                    log.error("Credit order with id='{}' not found", creditOrderId);
                    throw new CreditOrderNotFoundException(creditOrderId);
                });
    }
}
