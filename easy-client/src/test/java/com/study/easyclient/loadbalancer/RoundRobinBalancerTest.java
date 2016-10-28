package com.study.easyclient.loadbalancer;

import com.google.common.collect.Lists;
import com.study.easyclient.core.InnerClient;
import com.study.easyclient.core.InnerClientFactory;
import org.testng.annotations.Test;

import java.net.InetSocketAddress;
import java.util.List;

import static org.testng.Assert.*;

/**
 * @author jixu
 */
public class RoundRobinBalancerTest {

    private static class MockInnerClient extends InnerClient {
        public MockInnerClient(InetSocketAddress address) {
            super(address);
        }
        public void close() {}
    }

    private static class MockFactory implements InnerClientFactory {
        public InnerClient getInnerClient(InetSocketAddress address) {
            return new MockInnerClient(address);
        }
    }

    @Test
    public void test() throws Exception {
        RoundRobinBalancer<MockInnerClient> balancer = new RoundRobinBalancer<>(new MockFactory());
        List<InetSocketAddress> inetSocketAddresses = Lists.newArrayList(new InetSocketAddress(8080), new InetSocketAddress(8081));
        balancer.registerAddresses(inetSocketAddresses);

        InetSocketAddress address1 = balancer.getClient(null).getAddress();
        balancer.withDrawAddress(address1, true);
        InetSocketAddress address2 = balancer.getClient(null).getAddress();

        assertNotEquals(address1, address2);

        InetSocketAddress address3 = balancer.getClient(null).getAddress();
        InetSocketAddress address4 = balancer.getClient(null).getAddress();
        InetSocketAddress address5 = balancer.getClient(null).getAddress();
        InetSocketAddress address6 = balancer.getClient(null).getAddress();

        assertEquals(address3, address5);
        assertEquals(address4, address6);
        assertNotEquals(address3, address4);

        System.out.println(address1);
    }
}
