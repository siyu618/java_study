package com.study.paxos_zookeeper.chapter5.curator;

import com.study.ZKConfig;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.*;

/**
 * Created by tianyuzhi on 17/9/17.
 */
public class CreateSessionAsync {
    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(100, 3);
        ZKConfig zkConfig = ZKConfig.defaultConfig();
        CountDownLatch semaphore = new CountDownLatch(2);
        ExecutorService tp = Executors.newFixedThreadPool(2);

        CuratorFramework client =
                CuratorFrameworkFactory.builder()
                .connectString(zkConfig.getHostPort())
                .sessionTimeoutMs(zkConfig.getSessionTimeout())
                .connectionTimeoutMs(zkConfig.getConnectionTimeout())
                .namespace(zkConfig.getNamespace())
                .retryPolicy(retryPolicy)
                .build();


        String path = "/curator";
        client.start();

        System.out.println("Main thread:" + Thread.currentThread().getName());
        client.create()
                .creatingParentContainersIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                        System.out.println("event[code:" + curatorEvent.getResultCode() + ", type:" + curatorEvent.getType() + "]");
                        System.out.println("Thread of processResult:" + Thread.currentThread().getName());
                        semaphore.countDown();
                    }
                }, tp).forPath(path, "init".getBytes());

        client.create().creatingParentContainersIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                        System.out.println("event[code:" + curatorEvent.getResultCode() + ", type:" + curatorEvent.getType() + "]");
                        System.out.println("Thread of processResult:" + Thread.currentThread().getName());
                        semaphore.countDown();
                    }
                }).forPath(path, "init".getBytes());
        semaphore.await();
        tp.shutdown();
    }
}
