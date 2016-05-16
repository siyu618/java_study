package com.study.guava;


import com.google.common.base.Optional;

/**
 * Created by tianyuzhi on 16/5/12.
 */
public class OptionalTest {
    public static void main(String[] args) {
        OptionalTest optionalTest = new OptionalTest();
        Integer v1 = null;
        Integer v2 = new Integer(10);

        Optional a = Optional.fromNullable(v1);
        Optional b = Optional.of(v2);
        System.out.println(optionalTest.sum(a,b));

    }


    public Integer sum(Optional<Integer> a, Optional<Integer> b) {
        System.out.println("first param isPresent : " + a.isPresent());
        System.out.println("second param isPresent : " + b.isPresent());
        Integer v1 = a.or(0);
        Integer v2 = b.or(0);
        return  v1 + v2;
    }

}
