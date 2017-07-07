package com.study.artofjcp.ch4;

import java.util.concurrent.TimeUnit;

/**
 * Created by tianyuzhi on 17/7/7.
 */
public class Profiler {
    private static final ThreadLocal<Long> TIME_THREADLOCAL = ThreadLocal.withInitial(() -> System.currentTimeMillis());

    public static final void begin() {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }
    public static final long end() {
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }

    public static void main(String[] args) throws InterruptedException {
        Profiler.begin();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("cost:" + Profiler.end() + " mills");
    }
}
