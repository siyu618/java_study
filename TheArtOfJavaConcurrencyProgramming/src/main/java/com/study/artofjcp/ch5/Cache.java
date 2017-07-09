package com.study.artofjcp.ch5;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by tianyuzhi on 17/7/9.
 */
public class Cache {
    static Map<String, Object> map = new HashMap<>();
    static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    static Lock lockR = rwl.readLock();
    static Lock lockW = rwl.writeLock();

    public static final Object get(String key) {
        lockR.lock();
        try {
            return map.get(key);
        } finally {
            lockR.unlock();
        }
    }

    public static final Object put(String key, Object value) {
        lockW.lock();
        try {
            return map.put(key, value);
        } finally {
            lockW.unlock();
        }
    }

    public static final void clear() {
        lockW.lock();
        try {
            map.clear();
        } finally {
            lockW.unlock();
        }
    }
}
