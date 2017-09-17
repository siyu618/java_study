package com.study.paxos_zookeeper.chapter5.curator;

import com.study.ZKConfig;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.TimeUnit;

/**
 * Created by tianyuzhi on 17/9/17.
 */
public class PathChildCacheExample {
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


        String path = "/pathchildcache";
        // create
        client.start();

        PathChildrenCache cache = new PathChildrenCache(client, path, true);
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                switch (event.getType()) {
                    case CHILD_ADDED:
                        System.out.println("CHILD_ADDED," + event.getData().getPath());
                        break;
                    case CHILD_UPDATED:
                        System.out.println("CHILD_UPDATED," + event.getData().getPath());
                        break;
                    case CHILD_REMOVED:
                        System.out.println("CHILD_REMOVED," + event.getData().getPath());
                        break;
                    default:
                        break;
                }
            }
        });

//        client.create()
//                .creatingParentContainersIfNeeded()
//                .withMode(CreateMode.PERSISTENT)
//                .forPath(path, "init".getBytes());

        client.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path + "/c1");
        TimeUnit.SECONDS.sleep(1);
        client.delete().forPath(path + "/c1");
        TimeUnit.SECONDS.sleep(1);
        client.delete().forPath(path);
        TimeUnit.SECONDS.sleep(2);



    }
}
