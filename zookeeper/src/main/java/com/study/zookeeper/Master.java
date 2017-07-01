package com.study.zookeeper;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by tianyuzhi on 17/6/8.
 */
public class Master implements Watcher {

    private ZooKeeper zk;
    private String hostPort;
    private int sessionTimeout;
    private String serverId = Long.toString(ThreadLocalRandom.current().nextLong());
    private boolean isLeader = false;
    private String masterNode = "";

    Master(Config config){
        this.hostPort =  config.getString("zookeeper.zk_host");
        this.sessionTimeout = config.getInt("zookeeper.session_timeout");
        this.masterNode = config.getString("zookeeper.master_node");
    }

    void startZK() throws IOException {
        zk = new ZooKeeper(hostPort, sessionTimeout, this);
    }

    public boolean checkMaster() {
        while (true) {
            Stat stat = new Stat();
            byte[] data = new byte[0];
            try {
                data = zk.getData(masterNode, false, stat);
            } catch (KeeperException.NoNodeException e) {
                return false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (KeeperException.ConnectionLossException e) {
            } catch (KeeperException e) {
                e.printStackTrace();
            }
            isLeader = new String(data).equals(serverId);
        }
    }

    public void runForMaster() {
        while (true) {
            try {
                zk.create(masterNode, serverId.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                isLeader = true;
                break;
            } catch (KeeperException.NoNodeException e) {
                isLeader = false;break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (KeeperException e) {
                e.printStackTrace();
            }
            if (checkMaster()) break;
        }
    }

    public void stopZK() throws InterruptedException {
        zk.close();
    }


    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Config config = ConfigFactory.load();

        Master master = new Master(config);
        master.startZK();

        master.runForMaster();
        if (master.isLeader) {
            System.out.println("I am the leader");
            Thread.sleep(60000);
        }
        else {
            System.out.println("Someone else is the leader");
        }
        master.stopZK();
    }
}
