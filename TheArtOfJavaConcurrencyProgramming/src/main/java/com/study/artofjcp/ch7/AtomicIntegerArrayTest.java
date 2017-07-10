package com.study.artofjcp.ch7;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Created by tianyuzhi on 17/7/10.
 */
public class AtomicIntegerArrayTest {
    static int[] value = new int[] {1,2};
    static AtomicIntegerArray ai = new AtomicIntegerArray(value);

    public static void main(String[] args) {
        ai.getAndSet(0, 3);
        System.out.println(ai.get(0));
        System.out.println(value[0]);
    }
}
