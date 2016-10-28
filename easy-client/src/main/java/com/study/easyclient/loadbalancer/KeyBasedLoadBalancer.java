package com.study.easyclient.loadbalancer;

import com.google.common.collect.Lists;
import com.study.easyclient.core.InnerClient;
import com.study.easyclient.core.InnerClientFactory;
import com.study.easyclient.exceptions.LoadBalancerException;
import com.study.easyclient.hashfunction.ConsistentHash;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author jixu
 */
public class KeyBasedLoadBalancer<T extends InnerClient> extends AbstractLoadBalancer<T> {

    private final ConsistentHash<InetSocketAddress> consistentHash;
    private final Object lock = new Object();

    public KeyBasedLoadBalancer(InnerClientFactory innerClientFactory, int hashReplicaNumber) {
        super(innerClientFactory);
        consistentHash = new ConsistentHash<>(hashReplicaNumber, Lists.newArrayList());
    }

    protected InetSocketAddress getAddress(String key) throws LoadBalancerException {
        synchronized (lock) {
            InetSocketAddress address = consistentHash.get(key);
            if (address == null) {
                throw new LoadBalancerException("There is no available address now");
            }
            return address;
        }
    }

    protected void withDrawAddress(InetSocketAddress address, boolean isSuccess) {}

    public void registerAddresses(List<InetSocketAddress> addresses) throws LoadBalancerException {
        synchronized (lock) {
            consistentHash.clear();
            for (InetSocketAddress address : addresses) {
                consistentHash.add(address);
            }
        }
    }
}
