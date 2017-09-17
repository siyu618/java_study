package com.study.paxos_zookeeper.chapter5.curator;

import com.study.ZKConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * Created by tianyuzhi on 17/9/17.
 */
public class RecipesBarrier2 {

    public static void main(String[] args) throws Exception {
        String barrierPath = "/curator-recipes-barrier-path";
        int barrierCount = 5;
        for (int i = 0; i < barrierCount; i ++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
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
                        DistributedDoubleBarrier barrier = new DistributedDoubleBarrier(client, barrierPath, barrierCount);
                        System.out.println(Thread.currentThread().getName() + " enter");
                        barrier.enter();
                        System.out.println(Thread.currentThread().getName() + " start");
                        barrier.leave();
                        System.out.println(Thread.currentThread().getName() + " leave");
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }, "runner:" + i).start();
        }
        TimeUnit.SECONDS.sleep(3);
    }
}
