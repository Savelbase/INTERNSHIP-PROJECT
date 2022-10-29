package com.rmn.toolkit.deposits.query.service;

import com.rmn.toolkit.deposits.query.dto.success.DepositDto;
import com.rmn.toolkit.deposits.query.model.projection.DepositView;
import com.rmn.toolkit.deposits.query.repository.DepositRepository;
import com.rmn.toolkit.deposits.query.util.DepositUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepositService {
    private final DepositUtil depositUtil;

    public List<DepositDto> getDepositsDtoByClientId(String clientId) {
        List <DepositView> depositViewList = depositUtil.findAllByClientId(clientId);
        return depositUtil.createListDepositDto(depositViewList);
    }
}
