package com.study.concurrenty.non_blocking_algorithm;

/**
 * Created by tianyuzhi on 16/10/15.
 */
public class SingleWriterCounter {
    private volatile long count = 0;

    /**
     * only one thread may ever call this method
     * or it will lead to race conditons.
     */
    public void inc() {
        count ++;
    }

    /**
     * Many reading threads may call this method
     * @return
     */
    public long count() {
        return this.count;
    }
}
