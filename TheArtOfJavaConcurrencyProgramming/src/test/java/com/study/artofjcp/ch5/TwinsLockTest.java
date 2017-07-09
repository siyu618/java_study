package com.study.artofjcp.ch5;

import com.study.artofjcp.ch4.SleepUtils;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

import java.util.List;

/**
 * Created by tianyuzhi on 17/7/8.
 */
public class TwinsLockTest {

    @Test
    public void test() throws InterruptedException {
        final Lock lock = new TwinsLock();
        class Worker extends Thread {
             public void run() {
                while (true) {
                    lock.lock();
                    try {
                        SleepUtils.sleep(1);
                        System.out.println(Thread.currentThread().getName());
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i ++) {
            Worker w = new Worker();
            threads.add(w);
            w.setDaemon(true);
            w.start();
        }
        for (int i = 0; i < 10; i ++) {
            SleepUtils.sleep(1);
            System.out.println();
        }
//        for (Thread thread : threads) {
//            thread.join();
//        }
    }

}