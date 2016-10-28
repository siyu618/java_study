package com.study.easyclient.core;

import java.io.Closeable;
import java.net.InetSocketAddress;

/**
 * @author jixu
 */
public abstract class InnerClient implements Closeable {
    private final InetSocketAddress address;

    public InnerClient(InetSocketAddress address) {
        this.address = address;
    }

    public InetSocketAddress getAddress() {
        return address;
    }
}
