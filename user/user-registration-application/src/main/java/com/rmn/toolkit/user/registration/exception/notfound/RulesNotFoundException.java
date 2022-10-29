package com.rmn.toolkit.user.registration.exception.notfound;

import com.rmn.toolkit.user.registration.model.type.RuleType;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RulesNotFoundException extends ResponseStatusException {
    public RulesNotFoundException(RuleType ruleType) {
        super(HttpStatus.NOT_FOUND, String.format("Actual %s not found", ruleType));
    }
}
