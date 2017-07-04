package com.study.exam.game.impl;

import com.study.exam.game.NumberEntity;
import com.study.exam.game.Rule;
import com.study.exam.game.RuleResult;

import java.util.Map;

/**
 * Created by tianyuzhi on 17/7/3.
 */
public class TimesRule implements Rule<NumberEntity> {
    @Override
    public RuleResult apply(NumberEntity numberEntity, int number) {
        if (number <= 0) {
            return RuleResult.NOT_MATCHED;
        }
        Map<Integer, String> number2StringMap = numberEntity.getNumber2StringMap();
        StringBuilder sb = new StringBuilder();
        boolean isMatched = false;
        for (Map.Entry<Integer, String> entry : number2StringMap.entrySet()) {
            if (number % entry.getKey() == 0) {
                isMatched = true;
                sb.append(entry.getValue());
            }
        }
        RuleResult result;
        if (isMatched) {
            result = RuleResult.builder().match(true).value(sb.toString()).build();
        }
        else {
            result = RuleResult.NOT_MATCHED;
        }
        return result;
    }
}
