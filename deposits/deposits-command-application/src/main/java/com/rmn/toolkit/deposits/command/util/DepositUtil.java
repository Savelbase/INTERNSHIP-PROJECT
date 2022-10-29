package com.rmn.toolkit.deposits.command.util;

import com.rmn.toolkit.cryptography.RSAUtils;
import com.rmn.toolkit.deposits.command.exception.notfound.DepositNotFoundException;
import com.rmn.toolkit.deposits.command.model.Account;
import com.rmn.toolkit.deposits.command.model.Deposit;
import com.rmn.toolkit.deposits.command.repository.DepositRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class DepositUtil {

    private final DepositRepository depositRepository;
    private final RSAUtils rsaUtils;

    public Deposit findDepositByDepositId(String depositId) {
        return depositRepository.findById(depositId)
                .orElseThrow(() -> {
                    log.error("Deposit by id='{}' not found", depositId);
                    throw new DepositNotFoundException(depositId);
                });
    }


    @SneakyThrows
    public Account createAccount(String clientId) {
        return Account.builder()
                .id(UUID.randomUUID().toString())
                .clientId(clientId)
                .accountNumber(rsaUtils.encryptedDataByPublicKey(UUID.randomUUID().toString()))
                .build();
    }
}