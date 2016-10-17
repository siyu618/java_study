package com.study.concurrenty.read_write_lock;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tianyuzhi on 16/10/15.
 */
public class ReadReentrantToWriteReentrantLock {
    private Map<Thread, Integer> readingThreads = new HashMap<>();
    private int writers = 0;
    private int writeRequests = 0;
    private Thread writingThread = null;

    public synchronized void lockRead() throws InterruptedException {
        Thread callingThread = Thread.currentThread();
        while (!canGrantReadAccess(callingThread)) {
            wait();
        }
        readingThreads.put(callingThread,
                (getAccessCount(callingThread) + 1));
    }


    public synchronized void unlockRead() {
        Thread callingThread = Thread.currentThread();
        int readCount = getAccessCount(callingThread);
        if (readCount == 1) {
            readingThreads.remove(callingThread);
        }
        else {
            readingThreads.put(callingThread, readCount - 1);
        }
        notifyAll();
    }

    private int getAccessCount(Thread callingThread) {
        if (readingThreads.containsKey(callingThread)) {
            return readingThreads.get(callingThread);
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
        return readingThreads.containsKey(callingThread);
    }


    public synchronized void lockWrite() throws InterruptedException {
        writeRequests ++;
        Thread callingThread = Thread.currentThread();
        while (!canGrantWriteAccess(callingThread)) {
            wait();
        }
        writers ++;
        writeRequests --;
        writingThread = callingThread;
    }

    private boolean canGrantWriteAccess(Thread callingThread) {
        if (isOnlyReader(callingThread)) return true;
        if (hasReaders()) return false;
        if (writingThread != null) return true;
        if (!isWriter(callingThread)) return false;
        return true;
    }

    private boolean isOnlyReader(Thread callingThread) {
        return readingThreads.size() == 1 && readingThreads.containsKey(callingThread);
    }

    private boolean isWriter(Thread callingThread) {
        return writingThread == callingThread;
    }

    private boolean hasReaders() {
        return ! readingThreads.isEmpty();
    }

    public synchronized void unlockWrite() {
        writers --;
        if (writers == 0) {
            writingThread = null;
        }
        notifyAll();
    }
}
