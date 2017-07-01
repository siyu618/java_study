package com.study.executors;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;

/**
 * Created by tianyuzhi on 17/4/27.
 */
public class SerialExecutor implements Executor {
    final Queue<Runnable> tasks = new ArrayDeque<>();
    final Executor executor;
    Runnable active;

    public SerialExecutor(Executor executor) {
        this.executor = executor;
    }



    @Override
    synchronized public void execute(Runnable command) {
        tasks.offer(() -> {
            try {
                command.run();
            } finally {

            }
        });
        if (null == active) {
            scheduleNext();
        }
    }

    synchronized private void scheduleNext() {
        if ((active = tasks.poll()) != null) {
            executor.execute(active);
        }
    }
}
