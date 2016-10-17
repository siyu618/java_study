package com.study.concurrenty.read_write_lock;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tianyuzhi on 16/10/15.
 */
public class ReadReentrantWriteLock {
    private Map<Thread, Integer> readThreads = new HashMap<>();
    private int writers = 0;
    private int writeRequests = 0;


    public synchronized void lockRead() throws InterruptedException {
        Thread callingThread = Thread.currentThread();
        while (!canGrantReadAccess(callingThread)) {
            wait();
        }
        readThreads.put(callingThread,
                (getAccessCount(callingThread) + 1));
    }


    public synchronized void unlockRead() {
        Thread callingThread = Thread.currentThread();
        int readCount = getAccessCount(callingThread);
        if (readCount == 1) {
            readThreads.remove(callingThread);
        }
        else {
            readThreads.put(callingThread, readCount - 1);
        }
        notifyAll();
    }

    private int getAccessCount(Thread callingThread) {
        if (readThreads.containsKey(callingThread)) {
            return readThreads.get(callingThread);
        }
        else {
            return 0;
        }
    }

    private boolean canGrantReadAccess(Thread callingThread) {
        if (writers > 0) return false;
        if (isReader(callingThread)) return true;
        if (writeRequests > 0) return false;
        return true;
    }

    private boolean isReader(Thread callingThread) {
        return readThreads.containsKey(callingThread);
    }


    public synchronized void lockWrite() throws InterruptedException {
        writeRequests ++;
        while (readThreads.size() > 0 || writers > 0) {
            wait();
        }
        writers ++;
        writeRequests --;
    }

    public synchronized void unlockWrite() {
        writers --;
        notifyAll();
    }
}
