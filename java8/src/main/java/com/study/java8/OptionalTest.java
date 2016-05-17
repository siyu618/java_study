package com.study.java8;

import java.util.Optional;

/**
 * Created by tianyuzhi on 16/5/17.
 */
public class OptionalTest {
    public static void main(String[] args) {
        OptionalTest optionalTest = new OptionalTest();
        Integer v1 = null;
        Integer v2 = new Integer(10);
        Optional<Integer> a = Optional.ofNullable(v1);
        Optional<Integer> b = Optional.of(v2);

        System.out.println(optionalTest.sum(a,b));

    }

    public Integer sum(Optional<Integer> a, Optional<Integer> b) {
        System.out.println("First parameter is present: " + a.isPresent());
        System.out.println("Second parameter is present: " + b.isPresent());
        Integer value1 = a.orElse(new Integer(0));
        Integer value2 = b.get();
        return value1 + value2;
    }
}
