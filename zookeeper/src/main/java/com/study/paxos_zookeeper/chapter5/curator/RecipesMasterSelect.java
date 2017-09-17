package com.study.paxos_zookeeper.chapter5.curator;

import com.study.ZKConfig;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.TimeUnit;

/**
 * Created by tianyuzhi on 17/9/17.
 */
public class RecipesMasterSelect {

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


        String masterPath = "/curator-recipes-master-select-path";
        client.start();

        LeaderSelector selector = new LeaderSelector(client, masterPath,
                new LeaderSelectorListener() {
                    @Override
                    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
                        System.out.println("state changed");
                    }

                    @Override
                    public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
                        System.out.println("become leader.");
                        TimeUnit.SECONDS.sleep(3);
                        System.out.println("lease the master");
                    }
                });
        selector.autoRequeue();
        selector.start();
        TimeUnit.SECONDS.sleep(10);
    }
}
