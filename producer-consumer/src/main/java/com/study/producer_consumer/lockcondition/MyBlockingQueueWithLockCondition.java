package com.study.producer_consumer.lockcondition;

import com.study.producer_consumer.IBlockingQueue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by tianyuzhi on 18/5/14.
 */
public class MyBlockingQueueWithLockCondition implements IBlockingQueue {
    private final int capacity;
    private int size;
    private Queue<Integer> queue;
    private Lock lock = new ReentrantLock();
    private Condition notFullCondition = lock.newCondition(); // producerCondition
    private Condition notEmptyCondition = lock.newCondition(); // consumerCondition


    public MyBlockingQueueWithLockCondition(int capacity) {
        this.capacity = capacity;
        size = 0;
        queue = new LinkedList<>();
    }


    @Override
    public void produce(int val) {
        lock.lock();
        try {
            while (size == capacity) {
                try {
                    notFullCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.add(val);
            size ++;
            notEmptyCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int consume() {
        lock.lock();
        int retVal = -1;
        try {
            while (size == 0) {
                try {
                    notEmptyCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            size --;
            retVal = queue.remove();
            notFullCondition.signalAll();
        } finally {
            lock.unlock();
        }
        return retVal;
    }


    public static void main(String... args) throws InterruptedException {
        IBlockingQueue queue = new MyBlockingQueueWithLockCondition(10);

        int producerNum = 10;
        int consumerNum = 10;

        for (int i = 0; i < consumerNum; i ++) {
            final  int val = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " get " + queue.consume());
                }
            },"consumer-" + i).start();
        }


        for (int i = 0; i < producerNum; i ++) {
            final  int val = i;
            new Thread(() -> {
                queue.produce(val);
                System.out.println(Thread.currentThread().getName() + " put " + val);
            },"producer-" + i).start();
        }


        Thread.sleep(100000, 0);
    }

}
