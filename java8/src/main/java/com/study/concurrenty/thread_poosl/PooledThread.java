package com.study.concurrenty.thread_poosl;

import com.study.concurrenty.blockingqueue.BlockingQueue;

/**
 * Created by tianyuzhi on 16/10/15.
 */
public class PooledThread extends Thread {

    private BlockingQueue taskQueue = null;
    private boolean isStopped = false;

    public PooledThread(BlockingQueue queue) {
        this.taskQueue = queue;
    }

    public void run() {
        while (!isStopped()) {
            try {
                Runnable runnable = (Runnable) taskQueue.dequeue();
                runnable.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void doStop() {
        isStopped = true;
        this.interrupt();
    }

    public synchronized boolean isStopped(){
        return isStopped;
    }

}
