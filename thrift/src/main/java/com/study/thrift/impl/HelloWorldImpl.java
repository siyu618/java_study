package com.study.thrift.impl;

import com.study.thrift.demo.HelloWorldService;
import org.apache.thrift.TException;

/**
 * Created by tianyuzhi on 16/7/12.
 */
public class HelloWorldImpl implements HelloWorldService.Iface {
    @Override
    public String sayHello(String userName) throws TException {
        return "hello " + userName;
    }
}
