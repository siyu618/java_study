package com.study.executors;

import java.util.concurrent.Executor;

/**
 * Created by tianyuzhi on 17/4/27.
 */
public class DirectExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
