package com.study.zookeeper;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.log4j.Log4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by tianyuzhi on 17/6/8.
 */
@Log4j
public class Worker implements Watcher {

    private ZooKeeper zk;
    private String hostPort;
    private int sessionTimeout;
    private String serverId = Long.toString(ThreadLocalRandom.current().nextLong());
    static boolean isLeader = false;
    private String masterNode = "";
    private String status = "";
    private String workerName = "";


    Worker(Config config) {
        this.hostPort = config.getString("zookeeper.zk_host");
        this.sessionTimeout = config.getInt("zookeeper.session_timeout");
        this.masterNode = config.getString("zookeeper.master_node");
    }

    void startZK() throws IOException {
        zk = new ZooKeeper(hostPort, sessionTimeout, this);
    }




    public void register() {
        zk.create("/workers/worker-", serverId.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, new AsyncCallback.StringCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, String name) {
                switch (KeeperException.Code.get(rc)) {
                    case CONNECTIONLOSS:
                        register();
                        break;
                    case OK:
                        System.out.println("Register successfully: " + serverId );
                        System.out.println("Register successfully: " + name );
                        workerName = name;
                        break;
                    case NODEEXISTS:
                        System.out.println(serverId + " already registered.");
                        break;
                    default:
                        System.out.println("something went wrong:" + KeeperException.create(KeeperException.Code.get(rc), path));
                }
            }
        }, null);
    }

    public void stopZK() throws InterruptedException {
        zk.close();
    }


    AsyncCallback.StatCallback statusUpdateCallback = new AsyncCallback.StatCallback() {
        @Override
        public void processResult(int rc, String path, Object ctx, Stat stat) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    updateStatus((String)ctx);
                    return;
                case OK:
                    System.out.println("change status succeeded:" + stat.toString()) ;
            }
        }
    };

    synchronized private void updateStatus(String status) {
        if (status == this.status) {
            zk.setData(this.workerName, status.getBytes(), -1, statusUpdateCallback, status);
        }
    }

    public void setStatus(String status) {
        this.status = status;
        updateStatus(status);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Config config = ConfigFactory.load();

        Worker worker = new Worker(config);
        worker.startZK();

        worker.register();
        Thread.sleep(3000);
        worker.setStatus("ok");
        Thread.sleep(3000);



        worker.stopZK();
    }
}
