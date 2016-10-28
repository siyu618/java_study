package com.study.easyclient.loadbalancer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.study.easyclient.core.InnerClient;
import com.study.easyclient.core.InnerClientFactory;
import com.study.easyclient.exceptions.LoadBalancerException;
import com.study.easyclient.hashfunction.ConsistentHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author jixu
 */
public class ErrorHandledKeyBasedLoadBalancer<T extends InnerClient> extends AbstractLoadBalancer<T> {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandledKeyBasedLoadBalancer.class);

    private static final int ERROR_TIMES_THRESHOLD = 100;
    private static final long HANG_WAITING_INTERVAL = 60 * 1000;
    private static final long WAITING_CHECK_INTERVAL = 60 * 1000;
    private static final int HASH_REPLICA_NUMBER = 500;

    private final Object lock = new Object();

    private final ConsistentHash<InetSocketAddress> consistentHash;
    private final Map<InetSocketAddress, Integer> servingAddressMap;
    private final Set<Node> hangList;

    private final ScheduledExecutorService hangListCleaningScheduler;

    private final int errorTimesThreshold; // if error exceed this threshold, the address will be hanged.
    private final long hangWaitingInterval;

    public ErrorHandledKeyBasedLoadBalancer(InnerClientFactory innerClientFactory, String balancerName) {
        this(innerClientFactory, balancerName, HASH_REPLICA_NUMBER, ERROR_TIMES_THRESHOLD, HANG_WAITING_INTERVAL, WAITING_CHECK_INTERVAL);
    }

    public ErrorHandledKeyBasedLoadBalancer(InnerClientFactory innerClientFactory, String balancerName, int hashReplicaNumber,
                                            int errorTimesThreshold, long hangWaitingInterval, long waitingCheckInterval) {
        super(innerClientFactory);

        this.errorTimesThreshold = errorTimesThreshold;
        this.hangWaitingInterval = hangWaitingInterval;

        consistentHash = new ConsistentHash<>(hashReplicaNumber, Lists.newArrayList());
        servingAddressMap = Maps.newHashMap();
        hangList = Sets.newHashSet();
        hangListCleaningScheduler = Executors.newScheduledThreadPool(1,
                new ThreadFactoryBuilder().setNameFormat("loadbalancer-" + balancerName + "-thread-%d").build());
        hangListCleaningScheduler.scheduleAtFixedRate(() -> {
            long tsNow = System.currentTimeMillis();
            synchronized (lock) {
                // cleaning error times for serving addresses
                servingAddressMap.keySet().forEach(address -> {
                    servingAddressMap.put(address, 0);
                });
                // cleaning hang list
                hangList.forEach(node -> {
                    if (node.targetOnlineTs < tsNow) {
                        hangList.remove(node);
                        consistentHash.add(node.address);
                        servingAddressMap.put(node.address, 0);
                    }
                });
            }
        }, 0, waitingCheckInterval, TimeUnit.MILLISECONDS);
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

    protected void withDrawAddress(InetSocketAddress address, boolean isSuccess) {
        if (!isSuccess) {
            synchronized (lock) {
                // if the node is in serving addresses, remove that address or error time + 1.
                Integer errorTimes = servingAddressMap.get(address);
                if (errorTimes != null) {
                    if (errorTimes >= errorTimesThreshold) {
                        consistentHash.remove(address);
                        servingAddressMap.remove(address);
                        long tsNow = System.currentTimeMillis();
                        hangList.add(new Node(address, tsNow + hangWaitingInterval));
                    } else {
                        servingAddressMap.put(address, errorTimes+1);
                    }
                }
            }
        }
    }

    public void registerAddresses(List<InetSocketAddress> addresses) throws LoadBalancerException {
        Set<InetSocketAddress> addressSet = Sets.newHashSet(addresses);
        synchronized (lock) {
            // update consistent hash
            consistentHash.clear();
            addresses.forEach(consistentHash::add);

            // add new added address to error times map
            addresses.forEach(address -> {
                if (!servingAddressMap.containsKey(address)) {
                    servingAddressMap.put(address, 0);
                }
            });
            // remove unused address from error times map
            Iterator<Map.Entry<InetSocketAddress, Integer>> addressIter = servingAddressMap.entrySet().iterator();
            while (addressIter.hasNext()) {
                Map.Entry<InetSocketAddress, Integer> entry = addressIter.next();
                if (!addressSet.contains(entry.getKey())) {
                    addressIter.remove();
                }
            }
            // remove unused node from hangList
            Iterator<Node> nodeIter = hangList.iterator();
            while (nodeIter.hasNext()) {
                Node node = nodeIter.next();
                if (!addressSet.contains(node.address)) {
                    nodeIter.remove();
                }
            }
        }
    }

    private static class Node {
        public final InetSocketAddress address;
        public final long targetOnlineTs;
        public Node(InetSocketAddress address, long targetOnlineTs) {
            this.address = address;
            this.targetOnlineTs = targetOnlineTs;
        }
    }
}
