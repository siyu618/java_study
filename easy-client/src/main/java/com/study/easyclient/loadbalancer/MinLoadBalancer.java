package com.study.easyclient.loadbalancer;

import com.google.common.collect.Lists;
import com.study.easyclient.core.InnerClient;
import com.study.easyclient.core.InnerClientFactory;
import com.study.easyclient.exceptions.LoadBalancerException;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @author jixu
 */
public class MinLoadBalancer<T extends InnerClient> extends AbstractLoadBalancer<T> {
    private volatile List<AddressLoadPair> addressLoadPairs = Lists.newArrayList();

    private final Object lock = new Object();

    public MinLoadBalancer(InnerClientFactory innerClientFactory) {
        super(innerClientFactory);
    }

    public void registerAddresses(List<InetSocketAddress> addresses) throws LoadBalancerException {
        if (addresses == null) {
            throw new LoadBalancerException("registered addresses is null");
        }
        synchronized (lock) {
            addressLoadPairs = addresses.stream().map(AddressLoadPair::new).collect(Collectors.toList());
        }
    }

    protected InetSocketAddress getAddress(String key) throws LoadBalancerException {
        if (addressLoadPairs.isEmpty()) {
            throw new LoadBalancerException("There is no available address now");
        }

        synchronized (lock) {
            int index = ThreadLocalRandom.current().nextInt(addressLoadPairs.size());
            AddressLoadPair now = addressLoadPairs.get(index);
            for (AddressLoadPair addressLoadPair : addressLoadPairs) {
                if (addressLoadPair.load < now.load) {
                    now = addressLoadPair;
                }
            }
            now.load = now.load + 1;
            return now.address;
        }
    }

    protected void withDrawAddress(InetSocketAddress address, boolean isSuccess) {
        synchronized (lock) {
            for (AddressLoadPair addressLoadPair : addressLoadPairs) {
                if (addressLoadPair.address.equals(address)) {
                    if (addressLoadPair.load > 0) {
                        addressLoadPair.load = addressLoadPair.load - 1;
                    }
                    return;
                }
            }
        }
    }

    private static class AddressLoadPair {
        public InetSocketAddress address;
        public long load;

        public AddressLoadPair(InetSocketAddress address) {
            this.address = address;
            load = 0L;
        }
    }
}
