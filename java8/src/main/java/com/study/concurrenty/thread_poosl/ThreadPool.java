package com.study.concurrenty.thread_poosl;

import com.study.concurrenty.blockingqueue.BlockingQueue;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;
/**
 * Created by tianyuzhi on 16/10/15.
 */
public class ThreadPool {
    private BlockingQueue taskQueue = null;
    private List<PooledThread> threads = new ArrayList<>();
    private boolean isStopped = false;

    public ThreadPool(int noOfThreads, int maxNoOfTasks) {
        taskQueue = new BlockingQueue(maxNoOfTasks);
        for (int i = 0; i < noOfThreads; i ++) {
            threads.add(new PooledThread(taskQueue));
        }
        for (PooledThread thread : threads) {
            thread.start();
        }
    }

    public synchronized void execute(Runnable task) throws InterruptedException {
        if (isStopped) {
            throw new IllegalStateException("Thread pool is stopped!");
        }
        this.taskQueue.enqueue(task);
    }

    public synchronized void stop() {
        this.isStopped = true;
        for (PooledThread thread : threads) {
            thread.doStop();
        }
    }
}
