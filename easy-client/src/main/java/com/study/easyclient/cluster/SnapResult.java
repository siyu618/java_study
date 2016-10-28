package com.study.easyclient.cluster;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author jixu
 */
public class SnapResult {
    public final CompletableFuture<Void> future;
    public final List<InetSocketAddress> addresses;

    public SnapResult(CompletableFuture<Void> future, List<InetSocketAddress> addresses) {
        this.future = future;
        this.addresses = addresses;
    }
}
