package com.study.paxos_zookeeper.chapter5.curator;

import com.study.ZKConfig;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;
import sun.util.locale.provider.TimeZoneNameUtility;

import java.util.concurrent.*;

/**
 * Created by tianyuzhi on 17/9/17.
 */
public class RecipesBarrier {

    public static void main(String[] args) throws Exception {


        String barrierPath = "/curator-recipes-barrier-path";
        for (int i = 0; i < 5; i ++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + " setting");
                        ZKConfig zkConfig = ZKConfig.defaultConfig();
                        CuratorFramework client =
                                CuratorFrameworkFactory.builder()
                                        .connectString(zkConfig.getHostPort())
                                        .sessionTimeoutMs(zkConfig.getSessionTimeout())
                                        .connectionTimeoutMs(zkConfig.getConnectionTimeout())
                                        .namespace(zkConfig.getNamespace())
                                        .retryPolicy(new ExponentialBackoffRetry(100, 3))
                                        .build();
                        client.start();
                        DistributedBarrier barrier = new DistributedBarrier(client, barrierPath);
                        barrier.setBarrier();
                        barrier.waitOnBarrier();
                        System.out.println(Thread.currentThread().getName() + " start");
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }, "runner:" + i).start();
        }
        TimeUnit.SECONDS.sleep(3);

        ZKConfig zkConfig = ZKConfig.defaultConfig();
        CuratorFramework client =
                CuratorFrameworkFactory.builder()
                        .connectString(zkConfig.getHostPort())
                        .sessionTimeoutMs(zkConfig.getSessionTimeout())
                        .connectionTimeoutMs(zkConfig.getConnectionTimeout())
                        .namespace(zkConfig.getNamespace())
                        .retryPolicy(new ExponentialBackoffRetry(100, 3))
                        .build();
        client.start();
        DistributedBarrier barrier = new DistributedBarrier(client, barrierPath);
        barrier.removeBarrier();
    }
}
