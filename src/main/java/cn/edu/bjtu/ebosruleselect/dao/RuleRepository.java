package cn.edu.bjtu.ebosruleselect.dao;

import cn.edu.bjtu.ebosruleselect.entity.RuleSelect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends MongoRepository<RuleSelect, String> {
    public RuleSelect findRuleByRuleName(String ruleName);
    public RuleSelect findRuleByRuleExecute(int ruleExecute);
    public Page<RuleSelect> findAll(Pageable pageable);
    public RuleSelect findRuleByRuleId(String ruleId);
}
