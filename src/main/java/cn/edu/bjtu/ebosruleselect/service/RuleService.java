package cn.edu.bjtu.ebosruleselect.service;

import cn.edu.bjtu.ebosruleselect.entity.Rule;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RuleService {
    public Page<Rule> findAllRule(Pageable pageable);
    public List<Rule> findAllRule();
    public boolean addRule(Rule rule);
    public String deleteRule(String ruleId);
    public Rule findRuleByRuleId(String ruleId);
    public void changeRuleStatus(Rule rule, int status);
}
