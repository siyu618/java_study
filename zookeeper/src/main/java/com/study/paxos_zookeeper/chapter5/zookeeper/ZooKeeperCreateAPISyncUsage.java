package com.study.paxos_zookeeper.chapter5.zookeeper;

import com.study.ZKConfig;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by tianyuzhi on 17/9/14.
 */
public class ZooKeeperCreateAPISyncUsage implements Watcher {


    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZKConfig zkConfig = ZKConfig.defaultConfig();
        ZooKeeper zooKeeper = new ZooKeeper(
                zkConfig.getHostPort(),
                zkConfig.getSessionTimeout(),
                new ZooKeeperCreateAPISyncUsage()
        );
        System.out.println(zooKeeper.getState());
        try {
            connectedSemaphore.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Zookeeper session established.");
        String path = zooKeeper.create(
                "/zk-test-enphemeral",
                "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL
        );
        System.out.println("Success create znode:" + path);

        String path2 = zooKeeper.create(
                "/zk-test-enphemeral",
                "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL
        );
        System.out.println("Success create znode:" + path2);


    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("Receive watched Event:" + watchedEvent);
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()){
            connectedSemaphore.countDown();
        }
    }
}
