package com.study.producer_consumer;

/**
 *
 * @author tianyuzhi
 * @date 18/5/14
 */
public interface IBlockingQueue {
    void produce(int val);
    int consume();
}
