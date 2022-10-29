package com.rmn.toolkit.cards.query.util;

import com.rmn.toolkit.cards.query.exception.notfound.CardProductNotFoundException;
import com.rmn.toolkit.cards.query.model.CardProduct;
import com.rmn.toolkit.cards.query.repository.CardProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CardProductUtil {
    private final CardProductRepository cardProductRepository;

    public CardProduct findCardProductById(String id) {
        return cardProductRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Card product with id='{}' not found", id);
                    throw new CardProductNotFoundException(id);
                });
    }
}
