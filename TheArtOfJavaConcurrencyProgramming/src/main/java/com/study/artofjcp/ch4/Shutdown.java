package com.study.artofjcp.ch4;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * Created by tianyuzhi on 17/7/7.
 */
public class Shutdown {

    public static void main(String[] args) throws InterruptedException {
        Runner one  = new Runner();
        Thread countThread = new Thread(one, "CountThread");
        countThread.start();

        TimeUnit.SECONDS.sleep(1);

        countThread.interrupt();
        Runner two = new Runner();
        countThread = new Thread(two, "CountThread");
        countThread.start();
        TimeUnit.SECONDS.sleep(1);
        two.cancle();
    }

    private static class Runner implements Runnable {
        private long i ;
        private volatile boolean on = true;
        @Override
        public void run() {
            while (on && !Thread.currentThread().isInterrupted()) {
                i ++;
            }
            System.out.println("count i = " + i);
        }

        public void cancle() {
            on = false;
        }
    }
}
