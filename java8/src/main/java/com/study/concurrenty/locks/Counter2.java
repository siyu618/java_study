package com.study.concurrenty.locks;

/**
 * Created by tianyuzhi on 16/10/15.
 */
public class Counter2 {
    private int count = 0;

    public int inc() {
        synchronized (this) {
            return ++ count;
        }
    }
}
