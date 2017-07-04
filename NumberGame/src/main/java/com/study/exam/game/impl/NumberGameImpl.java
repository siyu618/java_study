package com.study.exam.game.impl;

import com.study.exam.game.NumberEntity;
import com.study.exam.game.NumberGame;
import com.study.exam.game.Rule;
import com.study.exam.game.RuleResult;
import com.study.exam.game.exception.InvalidNumberEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
/**
 * Created by tianyuzhi on 17/7/3.
 */
@Builder
@Data
public class NumberGameImpl implements NumberGame {
    private NumberEntity entity;
    private List<Rule> orderedRuleList; // only one rule will be applied

    @Override
    public String apply(int number) {
        for (Rule rule : orderedRuleList) {
            RuleResult ruleResult = null;
            try {
                ruleResult = rule.apply(entity, number);
            } catch (InvalidNumberEntity invalidNumberEntity) {
                invalidNumberEntity.printStackTrace();
            }
            if (null != ruleResult && ruleResult.isMatch()) {
                return ruleResult.getValue();
            }
        }
        return "";
    }

}
