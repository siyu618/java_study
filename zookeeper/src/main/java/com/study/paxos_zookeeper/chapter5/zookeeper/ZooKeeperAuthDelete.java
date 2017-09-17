package com.study.paxos_zookeeper.chapter5.zookeeper;

import com.study.ZKConfig;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by tianyuzhi on 17/9/14.
 */
public class ZooKeeperAuthDelete implements Watcher {


    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zooKeeper = null;
    private static Stat stat = new Stat();
    private static final String PATH = "/zk-book-auth-test";
    private static final String PATH2 = "/zk-book-auth-test/child";


    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZKConfig zkConfig = ZKConfig.defaultConfig();
        String path = "/zk-book";

         zooKeeper = new ZooKeeper(
                zkConfig.getHostPort(),
                zkConfig.getSessionTimeout(),
                new ZooKeeperAuthDelete()
        );
        System.out.println(zooKeeper.getState());
        try {
            connectedSemaphore.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Zookeeper session established.");
        System.out.println("###########\n\n\n");

        zooKeeper.addAuthInfo("digest", "foo:true".getBytes());
        zooKeeper.create(PATH, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        zooKeeper.create(PATH2, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);

        ZooKeeper zooKeeper2 = new ZooKeeper(
                zkConfig.getHostPort(),
                zkConfig.getSessionTimeout(),
                new ZooKeeperAuthDelete()
        );
        try {
            zooKeeper2.delete(PATH2, -1);
        }
        catch (Exception e) {
            System.out.println(e);
        }

        ZooKeeper zooKeeper3 = new ZooKeeper(
                zkConfig.getHostPort(),
                zkConfig.getSessionTimeout(),
                new ZooKeeperAuthDelete()
        );
        zooKeeper3.addAuthInfo("digest", "foo:true".getBytes());
        zooKeeper3.delete(PATH2, -1);

        System.out.println("delete successfully!");

        ZooKeeper zooKeeper4 = new ZooKeeper(
                zkConfig.getHostPort(),
                zkConfig.getSessionTimeout(),
                new ZooKeeperAuthDelete()
        );
        zooKeeper4.delete(PATH, -1);
        System.out.println("delete successfully!");

        TimeUnit.SECONDS.sleep(10);

    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("Receive watched Event:" + watchedEvent);
        if (Event.KeeperState.SyncConnected == watchedEvent.getState() && null == watchedEvent.getPath()){
            connectedSemaphore.countDown();
        }
    }
}
