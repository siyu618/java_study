package com.study.concurrent.tool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description Used to replace System.currentMill() under high TPS.
 * Refer to
 *    * http://pzemtsov.github.io/2017/07/23/the-slow-currenttimemillis.html
 *    * https://blog.csdn.net/wojiaoguchenghuanye/article/details/79801170
 * @author siyu
 */
public class SystemClock {
    private static class InstanceHolder {
        private static SystemClock INSTANCE = new SystemClock(1);
    }

    public static SystemClock getInstance() {
        return InstanceHolder.INSTANCE;
    }


    private SystemClock(long periodInMillSeconds) {
        this.periodInMillSeconds = periodInMillSeconds;
        now = new AtomicLong();
        scheduleClockUpdating();
    }

    private void scheduleClockUpdating() {
        now.set(System.currentTimeMillis());
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(runnable -> {
            Thread thread = new Thread(runnable, "System clock");
            thread.setDaemon(true);
            return thread;
        });
        scheduledExecutorService.scheduleAtFixedRate(() -> now.set(System.currentTimeMillis()),
                periodInMillSeconds,
                periodInMillSeconds,
                TimeUnit.MILLISECONDS);

    }

    private final long periodInMillSeconds;
    private final AtomicLong now;

    private long currentTimeMillis() {
        return now.get();
    }

    public long now() {
        return currentTimeMillis();
    }
}
