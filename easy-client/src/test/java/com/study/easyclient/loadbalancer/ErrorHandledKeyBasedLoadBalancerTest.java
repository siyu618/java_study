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
public class ErrorHandledKeyBasedLoadBalancerTest {
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
        ErrorHandledKeyBasedLoadBalancer<MockInnerClient> balancer =
                new ErrorHandledKeyBasedLoadBalancer<>(new MockFactory(), "mockbalancer", 500, 2, 500, 500);

        InetSocketAddress addr1 = new InetSocketAddress(8080);
        InetSocketAddress addr2 = new InetSocketAddress(8081);
        List<InetSocketAddress> inetSocketAddresses = Lists.newArrayList(addr1, addr2);

        balancer.registerAddresses(inetSocketAddresses);

        // testing consistent hash
        InetSocketAddress address1 = balancer.getClient(addr1.toString() + 0).getAddress();
        assertEquals(address1, addr1);
        balancer.withDrawAddress(address1, true);

        InetSocketAddress address2 = balancer.getClient(addr2.toString() + 1).getAddress();
        assertEquals(address2, addr2);
        balancer.withDrawAddress(address2, true);

        // testing hang list add & remove
        balancer.withDrawAddress(address1, false);
        balancer.withDrawAddress(address1, false);
        balancer.withDrawAddress(address1, false);
        InetSocketAddress address3 = balancer.getClient(addr1.toString() + 0).getAddress();
        assertEquals(address3, addr2);

        Thread.sleep(1000);
        InetSocketAddress address4 = balancer.getClient(addr1.toString() + 0).getAddress();
        assertEquals(address4, addr1);

        // testing scheduler error times cleanup
        balancer.withDrawAddress(address1, false);
        balancer.withDrawAddress(address1, false);
        Thread.sleep(1000);
        balancer.withDrawAddress(address1, false);
        InetSocketAddress address5 = balancer.getClient(addr1.toString() + 0).getAddress();
        assertEquals(address5, addr1);
    }
}
