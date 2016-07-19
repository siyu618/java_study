package com.study.thrift.services;

import com.study.thrift.demo.HelloWorldService;
import com.study.thrift.impl.HelloWorldImpl;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by tianyuzhi on 16/7/12.
 */
public class HelloWorldServer {
    public void start() throws TTransportException {
        TServerSocket serverTransport = new TServerSocket(7911);
        HelloWorldService.Processor processor = new HelloWorldService.Processor(new HelloWorldImpl());
        TBinaryProtocol.Factory factory = new TBinaryProtocol.Factory(true, true);
        TThreadPoolServer.Args args = new TThreadPoolServer.Args(serverTransport);
        args.processor(processor);
        args.protocolFactory(factory);
        TServer server = new TThreadPoolServer(args);
        System.out.println("Starting server on port 7911");
        server.serve();
    }
    public static void main(String[] args) throws TTransportException {
        HelloWorldServer helloWorldServer = new HelloWorldServer();
        helloWorldServer.start();
    }
}
