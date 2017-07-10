package com.study.artofjcp.ch8;

import java.util.concurrent.CountDownLatch;

/**
 * Created by tianyuzhi on 17/7/10.
 */
public class CountDownLatchTest {
    static CountDownLatch latch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        Thread parser1 = new Thread(() -> {
            System.out.println("parser1 done");
            latch.countDown();
        });
        Thread parser2 = new Thread(()->{
            System.out.println("parser2 done");
            latch.countDown();
        });
        parser1.start();
        parser2.start();
        latch.await();
        System.out.println("all parsers done");

    }
}
