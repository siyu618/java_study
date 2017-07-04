package com.study.exam.game.impl;

import com.study.exam.game.NumberEntity;
import com.study.exam.game.Rule;
import com.study.exam.game.RuleResult;
import com.study.exam.game.exception.InvalidNumberEntity;

import java.util.Map;

/**
 * Created by tianyuzhi on 17/7/3.
 */
public class ContainsFirstRule implements Rule<NumberEntity> {

    @Override
    public RuleResult apply(NumberEntity numberEntity, int number) throws InvalidNumberEntity {
        if (number <= 0) {
            return RuleResult.NOT_MATCHED;
        }
        Map<Integer, String> number2StringMap = numberEntity.getNumber2StringMap();
        if (null == number2StringMap || number2StringMap.size() < 1) {
            throw new InvalidNumberEntity("empty numberEntity which has no number");
        } else {
            Map.Entry<Integer, String> firstEntry = getFirstElement(number2StringMap);
            String tmp = String.valueOf(firstEntry.getKey());
            if (String.valueOf(number).indexOf(tmp) != -1) {
                return RuleResult.builder().match(true).value(firstEntry.getValue()).build();
            } else {
                return RuleResult.NOT_MATCHED;
            }
        }
    }

    private Map.Entry<Integer, String> getFirstElement(Map<Integer, String> number2StringMap) {
        if (null == number2StringMap) {
            return null;
        }
        for (Map.Entry<Integer, String> entry : number2StringMap.entrySet()) {
            return entry;
        }
        return null;
    }

}
