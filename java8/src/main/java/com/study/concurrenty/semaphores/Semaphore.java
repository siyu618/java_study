package com.study.concurrenty.semaphores;

import com.study.concurrenty.ThreadExample;

/**
 * Created by tianyuzhi on 16/10/15.
 */
public class Semaphore {
    private boolean signal = false;
    public synchronized void take() {
        this.signal = true;
        this.notify();
    }

    public synchronized  void release() throws InterruptedException {
        while (!this.signal) wait();
        this.signal = false;
    }

    public static class SendingThread extends Thread{
        Semaphore semaphore =null;
        public SendingThread(Semaphore semaphore) {
            this.semaphore = semaphore;
        }
        @Override public void run() {
            while (true) {
                // do something, then signal
                this.semaphore.take();
            }
        }
    }

    public static class ReceivingThread extends Thread{
        Semaphore semaphore = null;
        public ReceivingThread(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        public void run() {
            while (true) {
                try {
                    this.semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // receive signal, then do something
            }
        }
    }
    public static void main(String[] args)
    {
        Semaphore semaphore = new Semaphore();

    }
}
