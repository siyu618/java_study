
package com.study.paxos_zookeeper.chapter5.curator;

import com.study.ZKConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.TimeUnit;

/**
 * Created by tianyuzhi on 17/9/17.
 */
public class ZKPathsSample {

    public static void main(String[] args) throws Exception {


        String path = "/curator-zkpath-sample";
        ZKConfig zkConfig = ZKConfig.defaultConfig();
        CuratorFramework client =
                CuratorFrameworkFactory.builder()
                        .connectString(zkConfig.getHostPort())
                        .sessionTimeoutMs(zkConfig.getSessionTimeout())
                        .connectionTimeoutMs(zkConfig.getConnectionTimeout())
                        .namespace(zkConfig.getNamespace())
                        .retryPolicy(new ExponentialBackoffRetry(100, 3))
                        .build();
        client.start();

        ZooKeeper zooKeeper = client.getZookeeperClient().getZooKeeper();
       // System.out.println(ZKPaths.fixForNamespace(path, "/sub"));
        System.out.println(ZKPaths.makePath(path, "sub"));

        System.out.println(ZKPaths.getNodeFromPath(path + "/sub1"));
        ZKPaths.PathAndNode pathAndNode = ZKPaths.getPathAndNode(path + "/sub1");
        System.out.println(pathAndNode.getPath() + ":" + pathAndNode.getNode());

        String dir1 = path + "/child1";
        String dir2 = path + "/child2";
        ZKPaths.mkdirs(zooKeeper, dir1);
        ZKPaths.mkdirs(zooKeeper, dir2);

        System.out.println(ZKPaths.getSortedChildren(zooKeeper, path));
        ZKPaths.deleteChildren(client.getZookeeperClient().getZooKeeper(), path, true);
    }
}
