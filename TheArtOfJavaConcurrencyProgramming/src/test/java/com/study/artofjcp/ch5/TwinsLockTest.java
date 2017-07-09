package com.study.artofjcp.ch5;

import com.study.artofjcp.ch4.SleepUtils;
import org.testng.annotations.Test;

import java.util.concurrent.locks.Lock;


/**
 * Created by tianyuzhi on 17/7/8.
 */
public class TwinsLockTest {

    @Test
    public void test() {
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

        for (int i = 0; i < 10; i ++) {
            Worker w = new Worker();
            w.setDaemon(true);
            w.start();
        }
        for (int i = 0; i < 10; i ++) {
            SleepUtils.sleep(1);
            System.out.println();
        }
    }

}