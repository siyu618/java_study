package com.study.easyclient.core;

import java.net.InetSocketAddress;

/**
 * @author jixu
 */
public interface InnerClientFactory {
    InnerClient getInnerClient(InetSocketAddress address);
}
