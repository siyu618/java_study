package com.study.paxos_zookeeper.chapter5.zkclient;

import com.study.ZKConfig;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by tianyuzhi on 17/9/17.
 */
public class ZKClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        String path = "/zk-client-example";
        ZKConfig zkConfig = ZKConfig.defaultConfig();
        ZkClient zkClient = new ZkClient(
                zkConfig.getHostPort(),
                zkConfig.getSessionTimeout()
        );
        System.out.println("Zookeeper session established.");

        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChildren) throws Exception {
                System.out.println(parentPath + " 's child changed, current children:" + currentChildren);
            }
        });

        zkClient.createPersistent(path);
        TimeUnit.SECONDS.sleep(1);
        System.out.println(zkClient.getChildren(path));
        TimeUnit.SECONDS.sleep(1);
        zkClient.createPersistent(path + "/c1");
        TimeUnit.SECONDS.sleep(1);
        zkClient.delete(path + "/c1");
        TimeUnit.SECONDS.sleep(1);

        zkClient.delete(path);

        // get data
        String ePath = path + "/emp";

        zkClient.subscribeDataChanges(ePath, new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("Node[" + dataPath + "] changed, new data: " + data);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("Node[" + dataPath + "] deleted");

            }
        });
        zkClient.createPersistent(ePath, true);
        zkClient.writeData(ePath, "123");
        System.out.println((String) zkClient.readData(ePath));
        TimeUnit.SECONDS.sleep(1);
        zkClient.writeData(ePath, "446");
        Thread.sleep(3000);
        zkClient.delete(ePath);
        zkClient.delete(path);
        TimeUnit.SECONDS.sleep(5);


        //
        TimeUnit.SECONDS.sleep(10);

    }
}
