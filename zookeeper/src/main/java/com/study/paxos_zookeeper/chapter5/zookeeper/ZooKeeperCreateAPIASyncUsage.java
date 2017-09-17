package com.study.paxos_zookeeper.chapter5.zookeeper;

import com.study.ZKConfig;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by tianyuzhi on 17/9/14.
 */
public class ZooKeeperCreateAPIASyncUsage implements Watcher {


    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZKConfig zkConfig = ZKConfig.defaultConfig();
        ZooKeeper zooKeeper = new ZooKeeper(
                zkConfig.getHostPort(),
                zkConfig.getSessionTimeout(),
                new ZooKeeperCreateAPIASyncUsage()
        );
        System.out.println(zooKeeper.getState());
        try {
            connectedSemaphore.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Zookeeper session established.");
        zooKeeper.create(
                "/zk-test-enphemeral-",
                "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL,
                new IStringCallBack(),
                "I am a context"
        );
        zooKeeper.create(
                "/zk-test-enphemeral-",
                "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL,
                new IStringCallBack(),
                "I am a context"
        );

        zooKeeper.create(
                "/zk-test-enphemeral-",
                "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL,
                new IStringCallBack(),
                "I am a context"
        );
        Thread.sleep(10 * 1000);


    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("Receive watched Event:" + watchedEvent);
        if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
            connectedSemaphore.countDown();
        }
    }

    public static class IStringCallBack implements AsyncCallback.StringCallback {

        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            System.out.println("Create path result:[" + rc + "," + path + "," + ctx + ", real path name:" + name);

        }
    }
}
