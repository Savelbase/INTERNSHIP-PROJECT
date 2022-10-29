package com.rmn.toolkit.user.registration.util;

import com.rmn.toolkit.user.registration.exception.notfound.RulesNotFoundException;
import com.rmn.toolkit.user.registration.model.Rule;
import com.rmn.toolkit.user.registration.model.type.RuleType;
import com.rmn.toolkit.user.registration.repository.RulesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RulesUtil {
    private final RulesRepository rulesRepository;

    public Rule findByRuleTypeAndActual(RuleType ruleType, boolean actual) {
        return rulesRepository.findByRuleTypeAndActual(ruleType, actual)
                .orElseThrow(() -> {
                    log.error("Actual {} not found", ruleType);
                    throw new RulesNotFoundException(ruleType);
                });
    }
}