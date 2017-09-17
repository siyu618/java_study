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
public class ZooKeeperAuth implements Watcher {


    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zooKeeper = null;
    private static Stat stat = new Stat();
    private static final String PATH = "/zk-book-auth-test";


    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZKConfig zkConfig = ZKConfig.defaultConfig();
        String path = "/zk-book";

         zooKeeper = new ZooKeeper(
                zkConfig.getHostPort(),
                zkConfig.getSessionTimeout(),
                new ZooKeeperAuth()
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
        zooKeeper.create(PATH, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);

        ZooKeeper zooKeeper2 = new ZooKeeper(
                zkConfig.getHostPort(),
                zkConfig.getSessionTimeout(),
                new ZooKeeperAuth()
        );
        try {
            System.out.println(new String(zooKeeper2.getData(PATH, false, null)));
        }
        catch (Exception e) {
            System.out.println(e);
        }

        ZooKeeper zooKeeper3 = new ZooKeeper(
                zkConfig.getHostPort(),
                zkConfig.getSessionTimeout(),
                new ZooKeeperAuth()
        );
        zooKeeper3.addAuthInfo("digest", "foo:true".getBytes());
        System.out.println(new String(zooKeeper3.getData(PATH, false, null)));


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
