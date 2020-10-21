package cn.edu.bjtu.ebosruleselect.dao;

import cn.edu.bjtu.ebosruleselect.entity.Rule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends MongoRepository<Rule, String> {
    public Rule findRuleByRuleName(String ruleName);
    public Rule findRuleByRuleExecute(int ruleExecute);
    public Page<Rule> findAll(Pageable pageable);
    public Rule findRuleByRuleId(String ruleId);
}
