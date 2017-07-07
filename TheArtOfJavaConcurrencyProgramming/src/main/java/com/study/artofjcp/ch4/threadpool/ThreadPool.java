package com.study.artofjcp.ch4.threadpool;

/**
 * Created by tianyuzhi on 17/7/7.
 */
public interface ThreadPool<Job extends Runnable> {
    void execute(Job job);
    void shutdown();
    void addWorkers(int num);
    void removeWorker(int num) throws IllegalAccessException;
    int getJobSize();
}
