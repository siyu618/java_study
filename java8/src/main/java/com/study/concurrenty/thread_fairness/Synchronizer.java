package com.study.concurrenty.thread_fairness;

/**
 * Created by tianyuzhi on 16/10/13.
 */
public class Synchronizer {
    Lock lock = new Lock();
    public void doSynchronized() throws InterruptedException {
        this.lock.lock();
        //
        this.lock.unlock();
    }
}
