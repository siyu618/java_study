package com.study.concurrenty.synchronied;

/**
 * Created by tianyuzhi on 16/10/13.
 * The following two synchronized refer to the object owning the method.
 */
public class Instant {
    private int count = 0;

    public synchronized void add(int val) {
        count += val;
    }
    public void add2(int val) {
        synchronized (this) {
            count += val;
        }
    }
}
