package com.study.easyclient.cluster;

import com.google.common.collect.Sets;
import com.google.common.net.HostAndPort;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent.Type;
import org.apache.curator.utils.ZKPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @author jixu
 */
public class ZookeeperCluster implements Cluster {

    private static final Logger logger = LoggerFactory.getLogger(ZookeeperCluster.class);

    private static final int DEFAULT_PORT = 7070;

    private final Set<InetSocketAddress> underlyingSet = Sets.newHashSet();
    private CompletableFuture<Void> completableFuture = new CompletableFuture<>();

    public ZookeeperCluster(CuratorFramework zkClient, String path) {
        PathChildrenCache pathCache = new PathChildrenCache(zkClient, path, false);
        pathCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                PathChildrenCacheEvent.Type type = event.getType();
                if (type.equals(Type.CHILD_ADDED)) {
                    String path = event.getData().getPath();
                    String node = ZKPaths.getNodeFromPath(path);
                    logger.info("host added in zookeeper: " + path);
                    try {
                        InetSocketAddress address = generateAddress(node);
                        addAddress(address);
                    } catch (Exception e) {
                        logger.error("An error happened when generating address", e);
                    }
                } else if (type.equals(Type.CHILD_REMOVED)) {
                    String path = event.getData().getPath();
                    String node = ZKPaths.getNodeFromPath(path);
                    logger.info("host removed from zookeeper: " + path);
                    try {
                        InetSocketAddress address = generateAddress(node);
                        removeAddress(address);
                    } catch (Exception e) {
                        logger.error("An error happened when generating address", e);
                    }
                } else {
                    logger.info("Non Host-added/removed event: " + event.toString());
                }
            }
        });

        try {
            pathCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        } catch (Exception e) {
            logger.error("starting path children cache error", e);
            throw new RuntimeException(e);
        }
    }

    public synchronized SnapResult snap() {
        List<InetSocketAddress> addresses = new ArrayList<>(underlyingSet);
        return new SnapResult(completableFuture, addresses);
    }

    private synchronized void addAddress(InetSocketAddress address) {
        underlyingSet.add(address);
        CompletableFuture<Void> futureTmp = completableFuture;
        completableFuture = new CompletableFuture<>();
        futureTmp.complete(null);
    }

    private synchronized void removeAddress(InetSocketAddress address) {
        underlyingSet.remove(address);
        CompletableFuture<Void> futureTmp = completableFuture;
        completableFuture = new CompletableFuture<>();
        futureTmp.complete(null);
    }

    private static InetSocketAddress generateAddress(String node) {
        HostAndPort hostAndPort = HostAndPort.fromString(node).withDefaultPort(DEFAULT_PORT);
        String host = hostAndPort.getHostText();
        int port = hostAndPort.getPort();
        return new InetSocketAddress(host, port);
    }
}