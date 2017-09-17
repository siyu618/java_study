package com.study.paxos_zookeeper.chapter5.curator;

import com.study.ZKConfig;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.TimeUnit;

/**
 * Created by tianyuzhi on 17/9/17.
 */
public class NodeCacheExample {
    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(100, 3);
        ZKConfig zkConfig = ZKConfig.defaultConfig();


        CuratorFramework client =
                CuratorFrameworkFactory.builder()
                .connectString(zkConfig.getHostPort())
                .sessionTimeoutMs(zkConfig.getSessionTimeout())
                .connectionTimeoutMs(zkConfig.getConnectionTimeout())
                .namespace(zkConfig.getNamespace())
                .retryPolicy(retryPolicy)
                .build();


        String path = "/nodecache";
        // create
        client.start();
        client.create()
                .creatingParentContainersIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(path, "init".getBytes());

        NodeCache cache = new NodeCache(client, path, false);
        cache.start();

        cache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("path:" + cache.getCurrentData().getPath()  + ", Node data update, new data:" + new String(cache.getCurrentData().getData()));
            }
        });

        client.setData().forPath(path, "u".getBytes());
        TimeUnit.SECONDS.sleep(3);
        client.delete().deletingChildrenIfNeeded().forPath(path);
        TimeUnit.SECONDS.sleep(3);


    }
}
