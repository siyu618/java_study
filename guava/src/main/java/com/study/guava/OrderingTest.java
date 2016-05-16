package com.study.guava;

import com.google.common.collect.Ordering;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by tianyuzhi on 16/5/12.
 */
public class OrderingTest {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(
                5,2,15,51,53,35,45,32,43,16
        );
        Ordering ordering = Ordering.natural();
        System.out.println("Input list:" + numbers);
        Collections.sort(numbers, ordering);
        System.out.println("Sorted List:" + numbers);

        System.out.println("=========");
        System.out.println("List is sorted:" + ordering.isOrdered(numbers));
        System.out.println("Minimum:" + ordering.min(numbers));
        System.out.println("Maximum:" + ordering.max(numbers));

        Collections.sort(numbers, ordering.reverse());
        System.out.println("Reverse:" + numbers);

       // numbers.add(null);
        Collections.sort(numbers, ordering.nullsFirst());
        System.out.println("Null first Sorted List:" + numbers);
        System.out.println("=========");

        List<String> names = Arrays.asList(
                "Ram", "Shyam", "Mohan", "Sohan", "Ramesh",
                "Naresh", "Mahesh", null, "Vikas", "Deepak"
        );
        System.out.println("Another List:" + names);
        Collections.sort(names, ordering.nullsFirst().reverse());
        System.out.println("Null first then reverse:" +  names);


    }
}
