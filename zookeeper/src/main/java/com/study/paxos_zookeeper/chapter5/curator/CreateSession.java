package com.study.paxos_zookeeper.chapter5.curator;

import com.study.ZKConfig;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.TimeUnit;

/**
 * Created by tianyuzhi on 17/9/17.
 */
public class CreateSession {
    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(100, 3);
        ZKConfig zkConfig = ZKConfig.defaultConfig();

        CuratorFramework client2 =
                CuratorFrameworkFactory.newClient(
                        zkConfig.getHostPort(),
                        zkConfig.getConnectionTimeout(),
                        zkConfig.getSessionTimeout(),
                        retryPolicy
                );
        client2.start();

        CuratorFramework client =
                CuratorFrameworkFactory.builder()
                .connectString(zkConfig.getHostPort())
                .sessionTimeoutMs(zkConfig.getSessionTimeout())
                .connectionTimeoutMs(zkConfig.getConnectionTimeout())
                .namespace(zkConfig.getNamespace())
                .retryPolicy(retryPolicy)
                .build();


        String path = "/curator";
        // create
        client.start();
        client.create()
                .creatingParentContainersIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(path, "init".getBytes());
        // read
        Stat stat = new Stat();
        System.out.println(new String(client.getData().storingStatIn(stat).forPath(path)));

        // write
        client.setData().withVersion(stat.getVersion()).forPath(path, "123".getBytes());

        System.out.println(new String(client.getData().storingStatIn(stat).forPath(path)));

        // delete
        client.getData().storingStatIn(stat).forPath(path);
        client.delete().deletingChildrenIfNeeded().withVersion(stat.getVersion()).forPath(path);
        TimeUnit.SECONDS.sleep(10);
    }
}
