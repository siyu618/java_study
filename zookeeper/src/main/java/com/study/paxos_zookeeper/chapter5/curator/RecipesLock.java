package com.study.paxos_zookeeper.chapter5.curator;

import com.study.ZKConfig;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by tianyuzhi on 17/9/17.
 */
public class RecipesLock {

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


        String lockPath = "/curator-recipes-lock-path";
        client.start();

        final InterProcessMutex lock = new InterProcessMutex(client, lockPath);

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 30; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                        lock.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
                    String orderNo = sdf.format(new Date());
                    System.out.println("order number:" + orderNo) ;
                    try {
                        lock.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }).start();
        }
        countDownLatch.countDown();
        TimeUnit.SECONDS.sleep(10);
    }
}
