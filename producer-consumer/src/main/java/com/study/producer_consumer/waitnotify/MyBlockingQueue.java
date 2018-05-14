package com.study.producer_consumer.waitnotify;

import com.study.producer_consumer.IBlockingQueue;

import java.util.LinkedList;

/**
 * Created by tianyuzhi on 18/5/14.
 */
public class MyBlockingQueue implements IBlockingQueue {
    private final int capacity;
    private int size = 0;
    private LinkedList<Integer> list;

    public MyBlockingQueue(int capacity) {
        this.capacity = capacity;
        list = new LinkedList();
        size = 0;
    }

    @Override
    synchronized public void produce(int val) {
        while (size == capacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.addLast(val);
        size ++;
        notifyAll();
    }

    @Override
    synchronized public int consume() {
        while (size == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int val = list.removeFirst();
        size --;
        notifyAll();
        return val;
    }




    public static void main(String[] args) throws InterruptedException {
        IBlockingQueue queue = new MyBlockingQueue(10);
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
            new Thread(new Runnable() {
                @Override
                public void run() {
                    queue.produce(val);
                    System.out.println(Thread.currentThread().getName() + " put " + val);
                }
            },"producer-" + i).start();
        }


        Thread.sleep(100000, 0);

    }
}
