package com.study.java8;

/**
 * Created by tianyuzhi on 16/9/23.
 */
public class DefaultTest {

    public boolean testIntegerEqual5(Integer integer) {
        return integer == 5;
    }
    public static void main(String[] args) {
        DefaultTest defaultTest = new DefaultTest();
        System.out.println(new Integer(1) == new Integer(1));
        System.out.println(Integer.valueOf(1) == 1);
        System.out.println(new Integer(129) == new Integer(129));
        System.out.println(defaultTest.testIntegerEqual5(5));
        System.out.println(defaultTest.testIntegerEqual5(new Integer(5)));
        //defaultTest.testIntegerEqual5(null);

    }
}
