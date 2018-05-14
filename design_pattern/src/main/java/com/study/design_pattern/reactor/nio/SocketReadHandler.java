package com.study.design_pattern.reactor.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by tianyuzhi on 18/4/20.
 */
public class SocketReadHandler implements Runnable {
    private SocketChannel socketChannel;
    public SocketReadHandler(Selector selector, SocketChannel socketChannel) throws IOException {
        this.socketChannel = socketChannel;
        socketChannel.configureBlocking(false);

        SelectionKey selectionKey = socketChannel.register(selector, 0);

        selectionKey.attach(this);
        selector.wakeup();
    }

    @Override
    public void run() {
        ByteBuffer inputBuffer = ByteBuffer.allocate(1024);
        inputBuffer.clear();

        try {
            socketChannel.read(inputBuffer);
            // requestHandler(new Request(socket, btt;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
