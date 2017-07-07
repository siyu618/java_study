package com.study.artofjcp.ch4;

/**
 * Created by tianyuzhi on 17/7/6.
 */
public class ThreadState {
    public static void main(String[] args) {

        new Thread(new TimeWaiting(), "TimeWaitingThread").start();
        new Thread(new Waiting(), "WaitingThread").start();
        new Thread(new Blocked(), "BlockedThread1").start();
        new Thread(new Blocked(), "BlockedThread2").start();
    }

    static class TimeWaiting implements Runnable {

        @Override
        public void run() {
            while (true) {
                SleepUtils.sleep(100);
            }
        }
    }

    static class Waiting implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class Blocked implements Runnable {

        @Override
        public void run() {
            synchronized (Blocked.class) {
                while (true) {
                    SleepUtils.sleep(100);
                }
            }
        }
    }


}
