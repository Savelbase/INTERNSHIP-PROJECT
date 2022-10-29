package com.rmn.toolkit.deposits.query.util;

import com.rmn.toolkit.deposits.query.dto.success.DepositDto;
import com.rmn.toolkit.deposits.query.exception.notfound.DepositNotFoundException;
import com.rmn.toolkit.deposits.query.model.Deposit;
import com.rmn.toolkit.deposits.query.model.projection.DepositView;
import com.rmn.toolkit.deposits.query.repository.DepositRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Period;
import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class DepositUtil {

    private final DepositRepository depositRepository;

    public Deposit findDepositByDepositId(String depositId) {
        return depositRepository.findById(depositId)
                .orElseThrow(() -> {
                    log.error("Deposit by id='{}' not found", depositId);
                    throw new DepositNotFoundException(depositId);
                });
    }

    public DepositDto createDepositDto(DepositView depositView) {

        return DepositDto.builder()
                .depositName(depositView.getDepositProduct().getDepositName())
                .depositAmount(depositView.getDepositAmount())
                .depositPeriod(parseDepositPeriodToHumanReadable(calculateDepositPeriod(depositView)))
                .build();
    }

    public List<DepositView> findAllByClientId(String clientId) {
        return depositRepository.findAllByClientId(clientId);
    }

    public List<DepositDto> createListDepositDto(List<DepositView> depositViewList) {
        return depositViewList.stream().map(this::createDepositDto).toList();
    }


    private Period calculateDepositPeriod(DepositView depositView) {
        return Period.between(depositView.getStartDepositPeriod().toLocalDate(),
                depositView.getEndDepositPeriod().toLocalDate());
    }

    private String parseDepositPeriodToHumanReadable(Period period) {
        String humanReadablePeriod = "";

        humanReadablePeriod += period.getYears();

        if (period.getYears() == 1) {
            humanReadablePeriod += " year";
        } else {
            humanReadablePeriod += " years";
        }

        humanReadablePeriod = humanReadablePeriod + " " + period.getMonths();

        if (period.getMonths() == 1) {
            humanReadablePeriod += " month";
        } else {
            humanReadablePeriod += " months";
        }

        return humanReadablePeriod;
    }
}