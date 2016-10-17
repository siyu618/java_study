package com.study.concurrenty.semaphores;

/**
 * Created by tianyuzhi on 16/10/15.
 */
public class CountingSemaphore {
    private int signals = 0;
    public synchronized void take() {
        this.signals ++;
        this.notify();
    }
    public synchronized void release() throws InterruptedException {
        while (this.signals == 0) wait();
        this.signals --;
    }
}
