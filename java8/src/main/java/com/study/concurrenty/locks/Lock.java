package com.study.concurrenty.locks;

/**
 * Created by tianyuzhi on 16/10/15.
 */
public class Lock {
    private boolean isLocked = false;
    public synchronized void lock() throws InterruptedException {
        while (isLocked) {
            wait();
        }
        isLocked = true;
    }

    public synchronized void unlock() {
        isLocked = false;
        notify();
    }
}
