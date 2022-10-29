package com.rmn.toolkit.user.registration.repository;

import com.rmn.toolkit.user.registration.model.Rule;
import com.rmn.toolkit.user.registration.model.type.RuleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RulesRepository extends JpaRepository<Rule, String> {
    Optional<Rule> findByRuleTypeAndActual(RuleType ruleType, boolean actual);
}
