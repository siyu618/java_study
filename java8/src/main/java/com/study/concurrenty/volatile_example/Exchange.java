package com.study.concurrenty.volatile_example;



/**
 * Created by tianyuzhi on 16/10/13.
 */
public class Exchange {
    private Object object = null;
    private volatile boolean hasNewObject = false;

    public void put(Object obj) throws InterruptedException {
        while (hasNewObject) {
            Thread.sleep(1000);
        }
        this.object = obj;
        hasNewObject = true;
    }

    public Object take() throws InterruptedException {
        while (!hasNewObject) {
            Thread.sleep(1000);
        }
        Object res = object;
        hasNewObject = false;
        return res;
    }
}
