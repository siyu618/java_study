package com.study.thrift.services;

import com.facebook.nifty.core.NettyServerConfigBuilder;
import com.facebook.nifty.core.NettyServerTransport;
import com.facebook.nifty.core.ThriftServerDef;
import com.facebook.nifty.core.ThriftServerDefBuilder;
import com.study.thrift.demo.Hello;
import com.study.thrift.impl.HelloImpl;
import org.apache.thrift.TProcessor;
import org.jboss.netty.channel.group.DefaultChannelGroup;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by tianyuzhi on 16/7/19.
 */
public class HelloServerNifity {
    public static void main(String[] args) {
        // create the handler
        Hello.Iface iface = new HelloImpl();
        // create the processor
        TProcessor processor = new Hello.Processor<>(iface);
        // build the server definition
        ThriftServerDef serverDef = new ThriftServerDefBuilder()
                .withProcessor(processor)
                .listen(7911)
                .build();
        //create netty boss and executor thread pool
        ExecutorService bossExecutor = Executors.newCachedThreadPool();
        ExecutorService workerExecutor = Executors.newCachedThreadPool();
        // create the server transport
        final NettyServerTransport server = new NettyServerTransport(
                serverDef,
                new NettyServerConfigBuilder()
                        .setBossThreadExecutor(bossExecutor)
                        .setWorkerThreadExecutor(workerExecutor)
                        .build(),
                new DefaultChannelGroup()
        );


        // start the server
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    server.stop();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
