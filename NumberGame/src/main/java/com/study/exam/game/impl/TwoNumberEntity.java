package com.study.exam.game.impl;

import com.study.exam.game.NumberEntity;

import java.util.*;

/**
 * Created by tianyuzhi on 17/7/3.
 */
public class TwoNumberEntity implements NumberEntity {
    private static final String Fizz = "Fizz";
    private static final String Buzz = "Buzz";
    private static final int NUMBERS = 2;

    private List<Integer> numbers = new ArrayList<>(NUMBERS);
    private Map<Integer, String> number2StringMap = new LinkedHashMap<>(); // ordered by insert order, TODO:make immutable

    @Override
    public Map<Integer, String> getNumber2StringMap() {
        return number2StringMap;
    }

    public TwoNumberEntity(int a, int b, int c) {
        numbers.add(a);
        numbers.add(b);
        number2StringMap.put(a, Fizz);
        number2StringMap.put(b, Buzz);
    }

    @Override
    public boolean isValid() {
        if (numbers.size() != NUMBERS) {
            return false;
        }
        Set<Integer> set = new HashSet<>();
        for (Integer i : numbers) {
            if (set.contains(i) || i <= 0 || i >= 10) {
                return false;
            }
            set.add(i);
        }
        return true;
    }
}
