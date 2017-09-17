package com.study.paxos_zookeeper.chapter5.zookeeper;

import com.study.ZKConfig;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by tianyuzhi on 17/9/14.
 */
public class ZooKeeperConstructUsageWithPassword implements Watcher {


    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {
        ZKConfig zkConfig = ZKConfig.defaultConfig();
        ZooKeeper zooKeeper = new ZooKeeper(
                zkConfig.getHostPort(),
                zkConfig.getSessionTimeout(),
                new ZooKeeperConstructUsageWithPassword()
        );
        System.out.println(zooKeeper.getState());
        try {
            connectedSemaphore.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Zookeeper session established.");

        long sessionId = zooKeeper.getSessionId();
        byte[] password = zooKeeper.getSessionPasswd();
        zooKeeper = new ZooKeeper(
                zkConfig.getHostPort(),
                zkConfig.getSessionTimeout(),
                new ZooKeeperConstructUsageWithPassword(),
                1L,
                "test".getBytes()
        );

        zooKeeper = new ZooKeeper(
                zkConfig.getHostPort(),
                zkConfig.getSessionTimeout(),
                new ZooKeeperConstructUsageWithPassword(),
                sessionId,
                password
        );
        Thread.sleep(10 * 1000);


    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("Receive watched Event:" + watchedEvent);
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()){
            connectedSemaphore.countDown();
        }
    }
}
