package com.study.executors;

import java.util.concurrent.Executor;

/**
 * Created by tianyuzhi on 17/4/27.
 */
public class ThreadPerTaskExecutor implements Executor {

    @Override
    public void execute(Runnable command) {
        new Thread(command).start();
    }
}
