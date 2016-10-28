package com.study.easyclient.loadbalancer;

import com.google.common.collect.Lists;
import com.study.easyclient.core.InnerClient;
import com.study.easyclient.core.InnerClientFactory;
import org.testng.annotations.Test;

import java.net.InetSocketAddress;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * @author jixu
 */
public class KeyBasedLoadBalancerTest {
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
        KeyBasedLoadBalancer<MockInnerClient> balancer = new KeyBasedLoadBalancer<>(new MockFactory(), 500);
        InetSocketAddress addr1 = new InetSocketAddress(8080);
        InetSocketAddress addr2 = new InetSocketAddress(8081);
        List<InetSocketAddress> inetSocketAddresses = Lists.newArrayList(addr1, addr2);
        balancer.registerAddresses(inetSocketAddresses);

        InetSocketAddress address1 = balancer.getClient(addr1.toString() + 0).getAddress();
        assertEquals(address1, addr1);

        InetSocketAddress address2 = balancer.getClient(addr2.toString() + 1).getAddress();
        assertEquals(address2, addr2);
    }
}
