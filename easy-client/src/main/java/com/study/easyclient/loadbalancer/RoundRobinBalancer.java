package com.study.easyclient.loadbalancer;

import com.google.common.collect.Lists;
import com.study.easyclient.core.InnerClient;
import com.study.easyclient.core.InnerClientFactory;
import com.study.easyclient.exceptions.LoadBalancerException;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author jixu
 */
public class RoundRobinBalancer<T extends InnerClient> extends AbstractLoadBalancer<T> {
    private volatile List<InetSocketAddress> addresses = Lists.newArrayList();
    private int index = 0;

    public RoundRobinBalancer(InnerClientFactory innerClientFactory) {
        super(innerClientFactory);
    }

    protected InetSocketAddress getAddress(String key) throws LoadBalancerException {
        if (addresses.isEmpty()) {
            throw new LoadBalancerException("There is no available address now");
        }

        InetSocketAddress address;
        synchronized (this) {
            address = addresses.get(index);
            index = (index + 1) % addresses.size();
        }
        return address;
    }

    protected void withDrawAddress(InetSocketAddress address, boolean isSuccess) {}

    public void registerAddresses(List<InetSocketAddress> addresses) throws LoadBalancerException {
        if (addresses == null) {
            throw new LoadBalancerException("registered addresses is null");
        }
        synchronized (this) {
            this.addresses = addresses;
            index = 0;
        }
    }
}
