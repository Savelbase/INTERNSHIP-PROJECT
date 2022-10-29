package com.rmn.toolkit.credits.command.util;

import com.rmn.toolkit.credits.command.exception.notfound.CreditProductNotFoundException;
import com.rmn.toolkit.credits.command.model.CreditDictionary;
import com.rmn.toolkit.credits.command.repository.CreditDictionaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreditDictionaryUtil {
    private final CreditDictionaryRepository creditDictionaryRepository;

    public CreditDictionary findCreditProductByName(String name) {
        return creditDictionaryRepository.findCreditProductByName(name)
                .orElseThrow(() -> {
                    log.error("Credit product with name='{}' not found", name);
                    throw new CreditProductNotFoundException(name);
                });
    }
}
