package com.study.artofjcp.ch5;

import com.study.artofjcp.ch4.SleepUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by tianyuzhi on 17/7/9.
 */
public class FairAndUnfairTest {

    public void test(boolean fair) throws InterruptedException {
        if (fair) {
            test(new ReentrantLock2(true));
        } else {
            test(new ReentrantLock2(false));
        }
    }

    private void test(ReentrantLock2 lock) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Job(lock);
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        FairAndUnfairTest test = new FairAndUnfairTest();
        System.out.println("####fair");
        test.test(true);
        System.out.println("####unfair");
        test.test(false);
    }

    private static class Job extends Thread {
        private ReentrantLock2 lock;

        public Job(ReentrantLock2 lock) {
            this.lock = lock;
        }

        @Override
        public void run() {

            for (int i = 0; i < 2; i++) {
                lock.lock();
                try {
                    Collection<Thread> queuedThreads = lock.getQueuedThreads();
                    String name = "";
                    for (Thread thread : queuedThreads) {
                        name = name + " " + thread.getName();
                    }
                    System.out.println("Locked by " + Thread.currentThread().getName() + ", waiting by" + name);
                    SleepUtils.sleep(1);

                } finally {
                    lock.unlock();
                }
            }

        }
    }

    private static class ReentrantLock2 extends ReentrantLock {
        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        public Collection<Thread> getQueuedThreads() {
            List<Thread> arrayList = new ArrayList<>(super.getQueuedThreads());
            Collections.reverse(arrayList);
            return arrayList;
        }
    }
}
