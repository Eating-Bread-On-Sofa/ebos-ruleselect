package cn.edu.bjtu.ebosruleselect.service;

import cn.edu.bjtu.ebosruleselect.entity.RuleSelect;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RuleService {
    public Page<RuleSelect> findAllRule(Pageable pageable);
    public List<RuleSelect> findAllRule();
    public boolean addRule(RuleSelect rule);
    public String deleteRule(String ruleId);
    public RuleSelect findRuleByRuleId(String ruleId);
    public void changeRuleStatus(RuleSelect rule, int status);
}
