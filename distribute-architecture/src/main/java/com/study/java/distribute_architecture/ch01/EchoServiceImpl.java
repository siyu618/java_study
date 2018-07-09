package com.study.java.distribute_architecture.ch01;

/**
 * Created by tianyuzhi on 18/6/26.
 */
public class EchoServiceImpl implements IEchoService {
    @Override
    public String echo(String ping) {
        return ping != null ? ping + " --> I am OK." : " I am OK.";
    }
}
