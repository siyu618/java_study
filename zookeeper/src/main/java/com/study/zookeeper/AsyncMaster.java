package com.study.zookeeper;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.log4j.Log4j;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by tianyuzhi on 17/6/8.
 */
@Log4j
public class AsyncMaster implements Watcher {

    private ZooKeeper zk;
    private String hostPort;
    private int sessionTimeout;
    private String serverId = Long.toString(ThreadLocalRandom.current().nextLong());
    static boolean isLeader = false;
    private String masterNode = "";

    AsyncCallback.StringCallback masterCreateCallBack = (rc, path, ctx, name) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
                checkMaster();
                return;
            case OK:
                isLeader = true;
                break;
            default:
                isLeader = false;
                break;
        }
        System.out.println("I'am " + (isLeader ? "" : "not") + " the leader");
    };

    AsyncCallback.DataCallback masterCheckCallBack = (rc, path, ctx, data, stat) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
                checkMaster();
                break;
            case NONODE:
                runForMaster();
                break;
        }
    };

    AsyncMaster(Config config) {
        this.hostPort = config.getString("zookeeper.zk_host");
        this.sessionTimeout = config.getInt("zookeeper.session_timeout");
        this.masterNode = config.getString("zookeeper.master_node");
    }

    void startZK() throws IOException {
        zk = new ZooKeeper(hostPort, sessionTimeout, this);
    }


    private void checkMaster() {
        zk.getData(masterNode, false, masterCheckCallBack, null);
    }


    public void runForMaster() {
        zk.create(masterNode, serverId.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, masterCreateCallBack, null);
    }

    public void stopZK() throws InterruptedException {
        zk.close();
    }

    public void bootStrap() {
        createParent("/workers", new byte[0]);
        createParent("/assign", new byte[0]);
        createParent("/tasks", new byte[0]);
        createParent("/status", new byte[0]);
    }

    AsyncCallback.StringCallback getMasterCreateCallBack = new AsyncCallback.StringCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    createParent(path, (byte[]) ctx);
                    break;
                case OK:
                    System.out.println(path + " created");
                    break;
                case NODEEXISTS:
                    System.out.println(path + " already created");
                    break;
                default:
                    System.out.println("something went wrong:" + KeeperException.create(KeeperException.Code.get(rc), path));
            }
        }
    };

    void createParent(String path, byte[] data) {
        zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, getMasterCreateCallBack, null);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Config config = ConfigFactory.load();

        AsyncMaster master = new AsyncMaster(config);
        master.startZK();

        master.runForMaster();
        master.bootStrap();
        Thread.sleep(300);
        if (master.isLeader) {
            System.out.println("I am the leader");
            Thread.sleep(60000);
        } else {
            System.out.println("Someone else is the leader");
        }
        master.stopZK();
    }
}
