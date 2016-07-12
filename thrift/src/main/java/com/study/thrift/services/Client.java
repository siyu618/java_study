package com.study.thrift.services;

import com.study.thrift.demo.HelloWorldService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * Created by tianyuzhi on 16/7/12.
 */
public class Client {
    public static void main(String[] args) throws TException {
        TTransport transport = new TSocket("localhost", 7911);
        TProtocol protocol = new TBinaryProtocol(transport);
        HelloWorldService.Client client = new HelloWorldService.Client(protocol);
        transport.open();
        System.out.println("Client calls hello");
        System.out.println(client.sayHello("【Server】"));
        transport.close();
    }
}
