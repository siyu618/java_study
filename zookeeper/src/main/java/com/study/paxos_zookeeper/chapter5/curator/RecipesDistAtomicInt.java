package com.study.paxos_zookeeper.chapter5.curator;

import com.study.ZKConfig;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by tianyuzhi on 17/9/17.
 */
public class RecipesDistAtomicInt {

    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(100, 3);
        ZKConfig zkConfig = ZKConfig.defaultConfig();
        CuratorFramework client =
                CuratorFrameworkFactory.builder()
                        .connectString(zkConfig.getHostPort())
                        .sessionTimeoutMs(zkConfig.getSessionTimeout())
                        .connectionTimeoutMs(zkConfig.getConnectionTimeout())
                        .namespace(zkConfig.getNamespace())
                        .retryPolicy(retryPolicy)
                        .build();


        String lockPath = "/curator-recipes-distatomicint-path";
        client.start();

        final DistributedAtomicInteger atomicInteger = new DistributedAtomicInteger(client, lockPath,
                new RetryNTimes(3, 1000));
        AtomicValue<Integer> rc = atomicInteger.add(8);
        System.out.println("pre result:" + rc.preValue() );
        System.out.println("post result:" + rc.postValue() );

        TimeUnit.SECONDS.sleep(10);
    }
}
