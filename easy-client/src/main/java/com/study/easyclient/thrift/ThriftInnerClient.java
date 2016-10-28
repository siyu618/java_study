package com.study.easyclient.thrift;

import com.study.easyclient.core.InnerClient;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author jixu
 */
public class ThriftInnerClient<T extends TServiceClient> extends InnerClient {
    private static Logger logger = LoggerFactory.getLogger(ThriftInnerClient.class);

    private final T client;
    private final TTransport transport;

    public ThriftInnerClient(T client, TTransport transport, InetSocketAddress address) {
        super(address);
        this.client = client;
        this.transport = transport;
    }

    public T getThriftClient() {
        return client;
    }

    public void close() {
        transport.close();
    }
}
