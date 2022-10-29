package com.rmn.toolkit.credits.query.service;

import com.rmn.toolkit.credits.query.dto.response.success.CreditDto;
import com.rmn.toolkit.credits.query.model.projection.CreditView;
import com.rmn.toolkit.credits.query.repository.CreditRepository;
import com.rmn.toolkit.credits.query.util.CreditUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditService {
    private final CreditRepository creditRepository;
    private final CreditUtil creditUtil;

    @Transactional(readOnly = true)
    public List<CreditDto> getCreditsByClientId(String clientId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<CreditView> pageableCredits = creditRepository.getAllByAccount_ClientId(clientId, pageable);

        List<CreditDto> credits = new ArrayList<>();
        pageableCredits.forEach(credit -> credits.add(creditUtil.createCreditDto(credit)));
        return credits;
    }
}
