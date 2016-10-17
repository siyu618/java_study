package com.study.concurrenty.thread_signaling;

/**
 * Created by tianyuzhi on 16/10/13.
 *
 * Thread A and thread B must have the same reference to the same shared Singla Object.
 */
public class MySignal {
    protected boolean hasDataToProcess = false;

    public synchronized boolean isHasDataToProcess() {
        return hasDataToProcess;
    }
    public synchronized void setHasDataToProcess(boolean hasDataToProcess) {
        this.hasDataToProcess = hasDataToProcess;
    }
}
