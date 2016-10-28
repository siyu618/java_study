package com.study.easyclient.core;

import com.study.easyclient.cluster.Cluster;
import com.study.easyclient.cluster.SnapResult;
import com.study.easyclient.exceptions.LoadBalancerException;
import com.study.easyclient.loadbalancer.LoadBalancer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jixu
 */
public class BaseClient<T extends InnerClient> {
    private static final Logger logger = LoggerFactory.getLogger(BaseClient.class);

    protected final Cluster cluster;
    protected final LoadBalancer<T> loadBalancer;

    public BaseClient(Cluster cluster, LoadBalancer<T> loadBalancer) {
        this.cluster = cluster;
        this.loadBalancer = loadBalancer;
        snapAndRegister();
    }

    private boolean snapAndRegister() {
        SnapResult snapResult = cluster.snap();
        logger.info("addresses updated: " + snapResult.addresses);

        try {
            loadBalancer.registerAddresses(snapResult.addresses);
        } catch (LoadBalancerException e) {
            logger.error("LoadBalancer register addresses error", e);
        }
        snapResult.future.thenApply(v -> snapAndRegister());

        return true;
    }
}
