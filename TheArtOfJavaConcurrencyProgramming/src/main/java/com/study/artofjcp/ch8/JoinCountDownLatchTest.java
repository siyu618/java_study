package com.study.artofjcp.ch8;

/**
 * Created by tianyuzhi on 17/7/10.
 */
public class JoinCountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        Thread parser1 = new Thread(() -> {
            System.out.println("parser1 done");
        });
        Thread parser2 = new Thread(()->{
            System.out.println("parser2 done");
        });

        parser1.start();
        parser2.start();
        parser1.join();
        parser2.join();

        System.out.println("all paresers done");

    }
}
