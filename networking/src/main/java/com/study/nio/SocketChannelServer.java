package com.study.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by tianyuzhi on 17/1/19.
 */
public class SocketChannelServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        int i = 0;
        while (true) {
            System.out.println("in main:" + i);
            SocketChannel socketChannel = serverSocketChannel.accept();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ByteBuffer buf = ByteBuffer.allocate(48);
                    buf.clear();
                    String newData = "From server: ..." + System.currentTimeMillis();
                    buf.put(newData.getBytes());
                    buf.flip();
                    while (buf.hasRemaining()) {
                        try {
                            socketChannel.write(buf);
                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                    }
                    System.out.println(Thread.currentThread().getName() + ":done sending data to client");

                    buf.clear();
                    try {
                        socketChannel.read(buf);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + ": get from client :" + SocketChannelClient.printBuffer(buf));
                    try {
                        socketChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + ":DONE");
                }
            }, "name" + i++).start();
        }
    }
}
