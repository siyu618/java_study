package com.study.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by tianyuzhi on 17/1/19.
 */
public class SocketChannelClient {
    public static String printBuffer(ByteBuffer buffer) {
        StringBuilder sb = new StringBuilder(buffer.capacity());
        if (null != buffer) {
            while (buffer.hasRemaining()) {
                //System.out.print((char)buffer.get());
                sb.append((char)buffer.get());
            }
        }
        return sb.toString();

    }

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 9999));
        ByteBuffer buf = ByteBuffer.allocate(48);

        int bytesRead = socketChannel.read(buf);
        while (bytesRead != -1) {
            bytesRead = socketChannel.read(buf);

        }
        System.out.println("from server:" + printBuffer(buf));

        buf.clear();
        String newData = "New String to write to file..." + System.currentTimeMillis();

        buf.put(newData.getBytes());
        buf.flip();
        while (buf.hasRemaining()) {
            socketChannel.write(buf);
        }
        System.out.println("client done writing");
        socketChannel.close();


    }
}
