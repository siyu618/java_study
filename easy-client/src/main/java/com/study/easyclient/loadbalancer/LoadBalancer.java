package com.study.easyclient.loadbalancer;

import com.study.easyclient.core.InnerClient;
import com.study.easyclient.exceptions.LoadBalancerException;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author jixu
 */
public interface LoadBalancer<T extends InnerClient> {
    T getClient(String key) throws LoadBalancerException;
    void registerAddresses(List<InetSocketAddress> addresses) throws LoadBalancerException;
    void withDraw(T client, boolean isSuccess);
}