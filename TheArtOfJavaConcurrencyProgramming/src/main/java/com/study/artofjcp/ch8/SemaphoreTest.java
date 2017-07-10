package com.study.artofjcp.ch8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by tianyuzhi on 17/7/10.
 */
public class SemaphoreTest {
    public static int THREAD_COUNT = 30;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
    private static Semaphore semaphore = new Semaphore(10);
    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i ++) {
            threadPool.execute(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " save data");
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        threadPool.shutdown();
    }
}
