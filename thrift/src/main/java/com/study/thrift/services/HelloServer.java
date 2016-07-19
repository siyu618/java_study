package com.study.thrift.services;

import com.study.thrift.demo.Hello;
import com.study.thrift.impl.HelloImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by tianyuzhi on 16/7/19.
 */
public class HelloServer {
    public static void main(String[] args) {
        try {
            TServerSocket serverTransport = new TServerSocket(7911);
            TBinaryProtocol.Factory factory = new TBinaryProtocol.Factory();
            TProcessor processor = new Hello.Processor<>(new HelloImpl());
            TThreadPoolServer.Args serverArgs = new TThreadPoolServer.Args(serverTransport);
            serverArgs.processor(processor);
            serverArgs.protocolFactory(factory);
            TServer server = new TThreadPoolServer(serverArgs);
            System.out.println("Start on port 7911....");
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

}
