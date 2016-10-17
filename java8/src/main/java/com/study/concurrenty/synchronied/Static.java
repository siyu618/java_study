package com.study.concurrenty.synchronied;

/**
 * Created by tianyuzhi on 16/10/13.
 * The following two synchronized refer to the class object in the jvm
 */
public class Static {
    private static int count = 0;

    public synchronized void add(int val) {
        count += val;
    }

    public void add2(int val) {
        synchronized (Static.class) {
            count += val;
        }
    }
}
