package com.study.concurrent.tool;

import static org.junit.Assert.*;

public class SystemClockTest {

    private long count1(int N) {
        int total = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < N; i ++) {
            total += System.currentTimeMillis();
        }
        return System.currentTimeMillis() - start;
    }

    private long count2(int N) {
        int total = 0;
        long start = SystemClock.getInstance().now();
        for (int i = 0; i < N; i ++) {
            total += SystemClock.getInstance().now();
        }
        return SystemClock.getInstance().now() - start;
    }

    @org.junit.Test
    public void test() {
        int N = 100000000;
        System.out.println(SystemClock.getInstance().now());
        System.out.println(System.currentTimeMillis());
        long t1 = count1(N);
        long t2 = count2(N);
        System.out.println("t1:" + t1);
        System.out.println("t2:" + t2);
        System.out.println("t1 - t2:" + (t1 - t2));
    }
}