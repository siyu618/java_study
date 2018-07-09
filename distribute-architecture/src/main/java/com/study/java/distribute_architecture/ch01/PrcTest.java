package com.study.java.distribute_architecture.ch01;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by tianyuzhi on 18/6/26.
 */
public class PrcTest {
    public static void main(String...args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RpcExporter.export("localhost", 8080);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        RpcImporter<IEchoService> importer = new RpcImporter<>();
        IEchoService echo = importer.importer(EchoServiceImpl.class, new InetSocketAddress("localhost", 8080));
        System.out.println(echo.echo("Are you OK?"));
    }
}
