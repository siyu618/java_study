package com.study.concurrenty.blockingqueue;

import java.util.LinkedList;
import java.util.List;
/**
 * Created by tianyuzhi on 16/10/15.
 */
public class BlockingQueue<T> {
    private List<T> queue = new LinkedList<>();
    private final int limit;

    public BlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void enqueue(T t) throws InterruptedException {
        while (this.queue.size() == this.limit) {
            wait();
        }
        if (this.queue.size() == 0) {
            notify();
        }
        this.queue.add(t);
    }

    public synchronized T dequeue() throws InterruptedException {
        while (this.queue.size() == 0) {
            wait();
        }
        if (this.queue.size() == limit) {
            notifyAll();
        }
        return this.queue.remove(0);
    }
}
