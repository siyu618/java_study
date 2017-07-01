package com.study.artofjcp.ch2;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import java.util.List;
/**
 * Created by tianyuzhi on 17/7/1.
 */
public class Counter {
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private int i = 0;

    public static void main(String[] args) {
        final Counter cas = new Counter();
        List<Thread> threads = new ArrayList<>(600);
        long start = System.currentTimeMillis();
        for (int j = 0; j < 100; j ++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                   for (int i = 0; i < 1000;i ++) {
                       cas.count();
                       cas.safeCount();
                   }
                }
            });
            threads.add(thread);
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(cas.i);
        System.out.println(cas.atomicInteger.get());
        System.out.println(System.currentTimeMillis() - start);
    }

    private void safeCount() {
        for (;;) {
            int i = atomicInteger.get();
            boolean suc = atomicInteger.compareAndSet(i , ++i);
            if (suc) {
                break;
            }
        }
    }

    private void count() {
        i ++;
    }
}
