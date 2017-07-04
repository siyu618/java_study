package com.study.exam.game.impl;

import com.study.exam.game.NumberEntity;
import com.study.exam.game.Rule;
import com.study.exam.game.RuleResult;

/**
 * Created by tianyuzhi on 17/7/3.
 */
public class DefaultRule implements Rule<NumberEntity> {
    @Override
    public RuleResult apply(NumberEntity numberEntity, int number) {
        return RuleResult.builder().match(true).value(String.valueOf(number)).build();
    }
}
