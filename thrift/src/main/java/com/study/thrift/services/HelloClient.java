package com.study.thrift.services;

import com.study.thrift.demo.Hello;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * Created by tianyuzhi on 16/7/19.
 */
public class HelloClient {
    public static void main(String[] args) throws TException {
        TTransport transport = new TSocket("localhost", 7911);
        transport.open();
        TProtocol protocol = new TBinaryProtocol(transport);
        Hello.Client client = new Hello.Client(protocol);
        client.helloVoid();
        transport.close();

    }
}
