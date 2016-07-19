package com.study.thrift.services;

import com.study.thrift.callback.MethodCallback;
import com.study.thrift.demo.Hello;
import org.apache.thrift.TException;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;

import java.io.IOException;

/**
 * Created by tianyuzhi on 16/7/19.
 */
public class HelloAsyncClient {
    public static void main(String[] args) throws IOException, TException {
        TAsyncClientManager clientManager = new TAsyncClientManager();
        TNonblockingTransport transport = new TNonblockingSocket("localhost",10005);
        TProtocolFactory protocol = new TBinaryProtocol.Factory();
        Hello.AsyncClient asyncClient = new Hello.AsyncClient(protocol, clientManager, transport);
        System.out.println("client calls");
        MethodCallback callback = new MethodCallback();
        asyncClient.helloString("hello world", callback);
        Object res = callback.getResult();
        while (res == null) {
            System.out.println("wait result");
            res = callback.getResult();
        }
        System.out.println(((Hello.AsyncClient.helloString_call)res).getResult());
    }
}
