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
public class ZooKeeperGetDataAsync implements Watcher {


    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zooKeeper = null;
    private static Stat stat = new Stat();


    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZKConfig zkConfig = ZKConfig.defaultConfig();
        String path = "/zk-book";

         zooKeeper = new ZooKeeper(
                zkConfig.getHostPort(),
                zkConfig.getSessionTimeout(),
                new ZooKeeperGetDataAsync()
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
                    "123".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);
        } catch (Exception e) {
            System.out.println(e);
        }

        zooKeeper.getData(path, true, new IDataCallback(), stat);
        zooKeeper.setData(path,"123".getBytes(), -1);

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
        else if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
            try {
               zooKeeper.getData(watchedEvent.getPath(), true , new IDataCallback(), null);

            } catch (Exception e) {

            }
        }
    }
}

class IDataCallback implements AsyncCallback.DataCallback {

    @Override
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
        System.out.println(rc + ", " + path + "," + new String(data));
        System.out.println(
                stat.getCzxid() + "," +
                        stat.getMzxid() + "," +
                        stat.getVersion()
        );
    }
}
