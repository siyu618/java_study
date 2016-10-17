package com.study.concurrenty;

/**
 * Created by tianyuzhi on 16/10/12.
 */
public class CreatNewStartThread {
    public static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ": hello world!");
        }
    }

    public static class MyRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ": hello world!");
        }
    }


    public static void main(String[] args) {
        Thread myThread = new MyThread();
        myThread.start();

        Thread thread = new Thread(new MyRunnable(), "runnable");
        thread.start();


        Thread t1 = new Thread("t1") {
            @Override public void run() {
                System.out.println(Thread.currentThread().getName() + ": hello world!");
            }
        };
        System.out.println("t1.name=" + t1.getName());
    }
}
