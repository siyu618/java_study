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
public class Client implements Watcher {

    private ZooKeeper zk;
    private String hostPort;
    private int sessionTimeout;
    private String serverId = Long.toString(ThreadLocalRandom.current().nextLong());
    static boolean isLeader = false;
    private String masterNode = "";


    Client(Config config) {
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

    public String queueCommand(String command) throws Exception {
        while (true) {
            String name = "";
            try {
                 name = zk.create("/tasks/task-", command.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.PERSISTENT_SEQUENTIAL);
                return name;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (KeeperException.NodeExistsException e) {
                throw new Exception(name + " already appreas to be running");
            } catch (KeeperException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
    }

    public static void main(String[] args) throws Exception {
        Config config = ConfigFactory.load();

        Client client = new Client(config);
        client.startZK();
        String name = client.queueCommand("cmd");
        System.out.println(name);
        Thread.sleep(3000);
        client.stopZK();
    }
}
