package com.study.exam.game.impl;

import com.study.exam.game.NumberEntity;

import java.util.*;

/**
 * Created by tianyuzhi on 17/7/3.
 */
public class ThreeNumberEntity implements NumberEntity {
    private static final String Fizz = "Fizz";
    private static final String Buzz = "Buzz";
    private static final String Whizz = "Whizz";
    private static final int NUMBERS = 3;

    private List<Integer> numbers = new ArrayList<>(NUMBERS);
    private Map<Integer, String> number2StringMap = new LinkedHashMap<>(); // ordered by insert order, TODO:make immutable

    @Override
    public Map<Integer, String> getNumber2StringMap() {
        return number2StringMap;
    }

    public ThreeNumberEntity(int a, int b, int c) {
        numbers.add(a);
        numbers.add(b);
        numbers.add(c);
        number2StringMap.put(a, Fizz);
        number2StringMap.put(b, Buzz);
        number2StringMap.put(c, Whizz);
    }

    @Override
    public boolean isValid() {
        if (numbers.size() != NUMBERS) {
            return false;
        }
//        if (numbers.get(0) == numbers.get(1)
//                || numbers.get(1) == numbers.get(2)
//                || numbers.get(0) == numbers.get(2)) {
//            return false;
//        }
//        for (int i = 0; i < numbers.size() ; i ++ ) {
//            int cur = numbers.get(i);
//            if (cur >= 10 || cur <= 0) {
//                return false;
//            }
//        }
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
