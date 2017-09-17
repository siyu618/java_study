package com.study.paxos_zookeeper.chapter5.zookeeper;

import com.study.ZKConfig;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.List;

/**
 * Created by tianyuzhi on 17/9/14.
 */
public class ZooKeeperGetChildren implements Watcher {


    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zooKeeper = null;


    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZKConfig zkConfig = ZKConfig.defaultConfig();
        String path = "/zk-book";

         zooKeeper = new ZooKeeper(
                zkConfig.getHostPort(),
                zkConfig.getSessionTimeout(),
                new ZooKeeperGetChildren()
        );
        System.out.println(zooKeeper.getState());
        try {
            connectedSemaphore.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Zookeeper session established.");

        try {
            zooKeeper.create(
                    path,
                    "".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);
        } catch (Exception e) {
            System.out.println(e);
        }

        zooKeeper.create(
                path + "/c1",
                "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL);

        List<String> childrenList = zooKeeper.getChildren(path, true);
        System.out.println(childrenList);
        zooKeeper.create(
                path + "/c2",
                "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL);
        TimeUnit.SECONDS.sleep(10);

    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("Receive watched Event:" + watchedEvent);
        if (Event.KeeperState.SyncConnected == watchedEvent.getState() && null == watchedEvent.getPath()){
            connectedSemaphore.countDown();
        }
        else if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
            try {
                System.out.println("ReGet child:" + zooKeeper.getChildren(watchedEvent.getPath(), true));
            } catch (Exception e) {

            }
        }
    }
}
