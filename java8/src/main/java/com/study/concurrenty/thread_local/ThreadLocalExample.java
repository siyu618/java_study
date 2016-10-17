package com.study.concurrenty.thread_local;

/**
 * Created by tianyuzhi on 16/10/13.
 */
public class ThreadLocalExample {
    public static class MyRunnable implements Runnable {
        private ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
        @Override
        public void run() {
            threadLocal.set((int)(Math.random() * 100D));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
        }
    }
    public static void main(String[] args) throws InterruptedException {
        MyRunnable sharedRunnable = new MyRunnable();
        Thread t1 = new Thread(sharedRunnable, "name1");
        Thread t2 = new Thread(sharedRunnable, "name2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
