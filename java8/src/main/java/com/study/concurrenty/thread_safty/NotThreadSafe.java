package com.study.concurrenty.thread_safty;

/**
 * Created by tianyuzhi on 16/10/13.
 */
public class NotThreadSafe {
    StringBuilder builder = new StringBuilder();
    public void add(String text) {
        this.builder.append(text);
    }

    public static class MyRunnable implements Runnable
    {
        NotThreadSafe instance = null;
        public MyRunnable(NotThreadSafe notThreadSafe) {
            this.instance = notThreadSafe;
        }

        @Override
        public void run() {
            this.instance.add("some text");
            System.out.println(this.instance.builder);
        }
    }

    public static void main(String[] args) {
        NotThreadSafe instance = new NotThreadSafe();

        Thread t1 = new Thread(new MyRunnable(instance));
        Thread t2 = new Thread(new MyRunnable(instance));
        t1.start();
        t2.start();

    }
}
