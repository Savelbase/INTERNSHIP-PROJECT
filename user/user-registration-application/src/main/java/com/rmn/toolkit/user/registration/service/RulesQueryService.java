package com.rmn.toolkit.user.registration.service;

import com.rmn.toolkit.user.registration.model.Client;
import com.rmn.toolkit.user.registration.model.Rule;
import com.rmn.toolkit.user.registration.model.type.RuleType;
import com.rmn.toolkit.user.registration.util.ClientUtil;
import com.rmn.toolkit.user.registration.util.RulesUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RulesQueryService {
    private final RulesUtil rulesUtil;
    private final ClientUtil clientUtil;

    @Transactional(readOnly = true)
    public String getPrivacyPolicyText() {
        Rule privacyPolicy = rulesUtil.findByRuleTypeAndActual(RuleType.PRIVACY_POLICY, true);
        return privacyPolicy.getText();
    }

    @Transactional(readOnly = true)
    public String getRBSSRulesText(String clientId) {
        Client client = clientUtil.findClientById(clientId);
        clientUtil.checkIfClientRegisteredByMobilePhone(client.getMobilePhone());
        clientUtil.checkIfRequiredFieldIsAbsent(client);

        Rule rbssRules = rulesUtil.findByRuleTypeAndActual(RuleType.RBSS, true);
        return rbssRules.getText();
    }
}
