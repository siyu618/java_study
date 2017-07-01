package com.study.zookeeper;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.log4j.Log4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by tianyuzhi on 17/6/8.
 */
@Log4j
public class AdminClient implements Watcher {

    private ZooKeeper zk;
    private String hostPort;
    private int sessionTimeout;
    private String serverId = Long.toString(ThreadLocalRandom.current().nextLong());
    static boolean isLeader = false;
    private String masterNode = "";


    AdminClient(Config config) {
        this.hostPort = config.getString("zookeeper.zk_host");
        this.sessionTimeout = config.getInt("zookeeper.session_timeout");
        this.masterNode = config.getString("zookeeper.master_node");
    }

    void startZK() throws IOException {
        zk = new ZooKeeper(hostPort, sessionTimeout, this);
    }

    public void stopZK() throws InterruptedException {
        zk.close();
    }

    public String listState() throws KeeperException, InterruptedException {
        try {
            Stat stat = new Stat();
            byte masterDate[] = zk.getData("/master", false, stat);
            Date startDate = new Date(stat.getCtime());
            System.out.println("Mater:" + new String(masterDate) + " since " + startDate);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
        System.out.println("Workers");
        for(String w : zk.getChildren("/workers", false)) {
            byte[] data = zk.getData("/workers/" + w, false, null);
            System.out.println("\t" + w + ":" + new String(data));
        }
        System.out.println("Tasks");
        for(String t : zk.getChildren("/assign", false)) {
            System.out.println("\t" +t);
        }
        return null;
    }


    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
    }

    public static void main(String[] args) throws Exception {
        Config config = ConfigFactory.load();

        AdminClient adminClient = new AdminClient(config);
        adminClient.startZK();
        String name = adminClient.listState();
        System.out.println(name);
        Thread.sleep(3000);
        adminClient.stopZK();
    }
}
