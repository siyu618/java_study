package com.study.easyclient.cluster;

import com.google.common.collect.Lists;
import com.google.common.net.HostAndPort;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author jixu
 */
public class StaticCluster implements Cluster {

    private static final int DEFAULT_PORT = 7070;

    private final SnapResult result;

    public StaticCluster(String hosts) {
        String[] hostPorts = hosts.split(",");
        List<InetSocketAddress> addresses = Lists.newArrayListWithCapacity(hostPorts.length);
        for (String hostPort : hostPorts) {
            HostAndPort hostAndPort = HostAndPort.fromString(hostPort).withDefaultPort(DEFAULT_PORT);
            addresses.add(new InetSocketAddress(hostAndPort.getHostText(), hostAndPort.getPort()));
        }
        result = new SnapResult(new CompletableFuture<>(), addresses);
    }

    public SnapResult snap() {
        return result;
    }
}