package com.study.concurrenty.thread_signaling;

/**
 * Created by tianyuzhi on 16/10/13.
 */
public class SignalExample {
    public static void main(String[] args) throws InterruptedException {
        MySignal sharedSignal = new MySignal();

        Thread t1 = new Thread(() -> {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " set to true...");
            sharedSignal.setHasDataToProcess(true);
        }, "producer");

        Thread t2 = new Thread(() -> {
            while (!sharedSignal.isHasDataToProcess()) {
                //do thing .... busy waiting...
                // better to do some sleep
                System.out.println(Thread.currentThread().getName() + " waiting...");

            }

        }, "consumer");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
