package com.study.concurrenty.locks;

/**
 * Created by tianyuzhi on 16/10/15.
 */
public class ReentrantLock {
    boolean isLocked = false;
    Thread lockedBy = null;
    int lockedCount = 0;

    public synchronized void lock() throws InterruptedException {
        Thread curThread = Thread.currentThread();
        while (isLocked && lockedBy != curThread) {
            wait();
        }
        isLocked = true;
        lockedCount ++;
        lockedBy = curThread;
    }

    public synchronized void unlock() {
        if (Thread.currentThread() == this.lockedBy) {
            lockedCount --;
            if (lockedCount == 0) {
                isLocked = false;
                notify();
            }
        }
    }
}
