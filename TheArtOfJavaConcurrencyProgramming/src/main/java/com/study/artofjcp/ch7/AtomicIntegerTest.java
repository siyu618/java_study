package com.study.artofjcp.ch7;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tianyuzhi on 17/7/10.
 */
public class AtomicIntegerTest {
    static AtomicInteger ai = new AtomicInteger(1);

    public static void main(String[] args) {
        System.out.println(ai.getAndIncrement());
        System.out.println(ai.get());
    }
}
