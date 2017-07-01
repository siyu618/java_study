package com.study.artofjcp.ch1;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tianyuzhi on 17/7/1.
 */
public class ConcurrencyTest {

    private static final long COUNT = 10000001;

    public static void main(String[] args) {
        concurrency();
        serial();
    }

    private static void serial() {
        long start = System.currentTimeMillis();
        AtomicInteger a = new AtomicInteger(0);
        int c = 0;
        for (long i = 0; i < COUNT; i ++) {
            c += 5;
        }
        a.set(c+1);

        int b = 0;
        for (long i = 0; i < COUNT; i ++) {
            b --;
        }
        long cost = System.currentTimeMillis() - start;
        System.out.println("concurrency:" + cost + "ms,b=" + b + ",a=" + a.get());
    }

    private static void concurrency() {
        long start = System.currentTimeMillis();
        AtomicInteger a = new AtomicInteger(0);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int c = 0;
                for (long i = 0; i < COUNT; i++) {
                    c += 5;
                }
                a.set(c+1);
            }
        });
        thread.start();
        int b = 0;
        for (long i = 0; i < COUNT; i ++) {
            b --;
        }
        long cost = System.currentTimeMillis() - start;
        System.out.println("serial:" + cost + "ms,b=" + b + ",a=" + a.get());
    }
}
