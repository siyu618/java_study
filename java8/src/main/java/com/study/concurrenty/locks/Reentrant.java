package com.study.concurrenty.locks;

/**
 * Created by tianyuzhi on 16/10/15.
 */
public class Reentrant {

    public synchronized void out() {
        inner();
    }

    public synchronized void inner() {
    }
}
