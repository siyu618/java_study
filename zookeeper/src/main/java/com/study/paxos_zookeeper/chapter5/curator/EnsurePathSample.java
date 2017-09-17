
package com.study.paxos_zookeeper.chapter5.curator;

import com.study.ZKConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.EnsurePath;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.ZooKeeper;

/**
 * Created by tianyuzhi on 17/9/17.
 */
public class EnsurePathSample {

    public static void main(String[] args) throws Exception {


        String path = "/curator-ensure-path-sample";
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

        EnsurePath ensurePath = new EnsurePath(path);
        ensurePath.ensure(client.getZookeeperClient());
        ensurePath.ensure(client.getZookeeperClient());

        //
        EnsurePath ensurePath1 = client.newNamespaceAwareEnsurePath("/c1");
        ensurePath1.ensure(client.getZookeeperClient());

    }
}
