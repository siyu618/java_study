package com.study.producer_consumer.blocking_queue;

import com.study.producer_consumer.IBlockingQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by tianyuzhi on 18/5/14.
 */
public class MyBlockingQueue implements IBlockingQueue {
    private BlockingQueue<Integer> queue;

    public MyBlockingQueue(int capacity) {
        queue = new LinkedBlockingQueue<>(capacity);
    }

    @Override
    public void produce(int val) {
        try {
            queue.put(val);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int consume() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void main(String[] args) throws InterruptedException {
        IBlockingQueue queue = new MyBlockingQueue(10);
        int producerNum = 10;
        int consumerNum = 10;

        for (int i = 0; i < consumerNum; i ++) {
            final  int val = i;
            new Thread(() -> System.out.println(Thread.currentThread().getName() + " get " + queue.consume()),"consumer-" + i).start();
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
