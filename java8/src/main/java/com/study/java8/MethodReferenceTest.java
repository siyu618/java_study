package com.study.java8;

import java.util.Arrays;
import java.util.List;

/**
 * Created by tianyuzhi on 16/5/17.
 */
public class MethodReferenceTest {
    public static void main(String[] args) {
        List<String> names = Arrays.asList(
                "Mahesh",
                "Suresh",
                "Ramesh",
                "Naresh",
                "Kalpesh"
        );
        names.forEach(System.out::println);
    }
}
