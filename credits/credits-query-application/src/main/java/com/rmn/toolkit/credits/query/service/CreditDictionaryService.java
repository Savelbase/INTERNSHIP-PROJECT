package com.rmn.toolkit.credits.query.service;

import com.rmn.toolkit.credits.query.dto.response.success.CreditProductDto;
import com.rmn.toolkit.credits.query.dto.response.success.CreditProductAgreementDto;
import com.rmn.toolkit.credits.query.model.CreditDictionary;
import com.rmn.toolkit.credits.query.repository.CreditDictionaryRepository;
import com.rmn.toolkit.credits.query.util.CreditDictionaryUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditDictionaryService {
    private final CreditDictionaryRepository creditDictionaryRepository;
    private final CreditDictionaryUtil creditDictionaryUtil;

    @Transactional(readOnly = true)
    public List<CreditProductDto> getAllCreditProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CreditDictionary> creditDictionary = creditDictionaryRepository.findAll(pageable);
        
        List<CreditProductDto> creditProducts = new ArrayList<>();
        creditDictionary.stream()
                .forEach(creditProduct -> creditProducts.add(creditDictionaryUtil.createCreditProductDto(creditProduct)));
        return creditProducts;
    }

    @Transactional(readOnly = true)
    public CreditProductDto getCreditProductById(String creditProductId) {
        CreditDictionary creditProduct = creditDictionaryUtil.findCreditProductById(creditProductId);
        return creditDictionaryUtil.createCreditProductDto(creditProduct);
    }

    @Transactional(readOnly = true)
    public CreditProductAgreementDto getCreditProductAgreementById(String creditProductId) {
        CreditDictionary creditProduct = creditDictionaryUtil.findCreditProductById(creditProductId);
        return creditDictionaryUtil.createCreditProductAgreementDto(creditProduct);
    }
}
