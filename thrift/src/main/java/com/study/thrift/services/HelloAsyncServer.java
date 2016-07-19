package com.study.thrift.services;

import com.study.thrift.demo.Hello;
import com.study.thrift.impl.HelloImpl;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by tianyuzhi on 16/7/19.
 */
public class HelloAsyncServer {
    public static void main(String[] args) throws TTransportException {
        TNonblockingServerTransport serverTransport;
        serverTransport = new TNonblockingServerSocket(10005);
        Hello.Processor processor = new Hello.Processor<>(new HelloImpl());
        TNonblockingServer.Args serverArgs = new TNonblockingServer.Args(serverTransport);
        serverArgs.processor(processor);
        TServer server = new TNonblockingServer(serverArgs);
        System.out.println("Start server on 10005");
        server.serve();
    }
}
