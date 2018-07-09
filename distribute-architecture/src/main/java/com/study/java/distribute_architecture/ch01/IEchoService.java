package com.study.java.distribute_architecture.ch01;

/**
 * Created by tianyuzhi on 18/6/26.
 */
public interface IEchoService {
    default String echo(String ping) {
        return null;
    }
}
