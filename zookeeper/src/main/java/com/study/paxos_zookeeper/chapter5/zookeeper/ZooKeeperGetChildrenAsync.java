package com.study.paxos_zookeeper.chapter5.zookeeper;

import com.study.ZKConfig;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by tianyuzhi on 17/9/14.
 */
public class ZooKeeperGetChildrenAsync implements Watcher {


    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zooKeeper = null;


    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZKConfig zkConfig = ZKConfig.defaultConfig();
        String path = "/zk-book";

         zooKeeper = new ZooKeeper(
                zkConfig.getHostPort(),
                zkConfig.getSessionTimeout(),
                new ZooKeeperGetChildrenAsync()
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
        zooKeeper.getChildren(path, true, new IChildren2Callback(), null);
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

class IChildren2Callback implements AsyncCallback.Children2Callback {

    @Override
    public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
        System.out.println("Get children znode result : [response code:" + rc + ", param path:" + path
        + ", ctx:" + ctx + ",children list:" + children + ", stat:" + stat);
    }
}
