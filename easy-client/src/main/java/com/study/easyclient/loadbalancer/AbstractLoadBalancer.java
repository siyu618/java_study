package com.study.easyclient.loadbalancer;

import com.study.easyclient.core.InnerClient;
import com.study.easyclient.core.InnerClientFactory;
import com.study.easyclient.exceptions.LoadBalancerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author jixu
 */
public abstract class AbstractLoadBalancer<T extends InnerClient> implements LoadBalancer<T> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractLoadBalancer.class);

    private final InnerClientFactory innerClientFactory;

    public AbstractLoadBalancer(InnerClientFactory innerClientFactory) {
        this.innerClientFactory = innerClientFactory;
    }

    public T getClient(String key) throws LoadBalancerException {
        InetSocketAddress address = getAddress(key);
        T client = (T) innerClientFactory.getInnerClient(address);
        if (client == null) {
            throw new LoadBalancerException("Client factory get inner client failed");
        }
        return client;
    }

    protected abstract InetSocketAddress getAddress(String key) throws LoadBalancerException;

    /**
     * @param address address to be withdraw
     * @param isSuccess whether this address was successful used for client call
     */
    protected abstract void withDrawAddress(InetSocketAddress address, boolean isSuccess);

    public void withDraw(T client, boolean isSuccess) {
        try {
            client.close();
            withDrawAddress(client.getAddress(), isSuccess);
        } catch (IOException e) {
            logger.error("LoadBalancer withdraw client failed", e);
        }
    }
}
