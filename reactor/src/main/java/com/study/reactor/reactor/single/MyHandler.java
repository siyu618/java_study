package com.study.reactor.reactor.single;

import com.study.reactor.config.ServerConfig;
import com.study.reactor.config.SocketState;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class MyHandler implements Runnable {
    private final SocketChannel socketChannel;
    private final Selector selector;
    private final SelectionKey selectionKey;

    private ByteBuffer input;
    private ByteBuffer output;
    private SocketState socketState;
    public MyHandler(Selector selector, SocketChannel socketChannel, ServerConfig serverConfig) throws IOException {
        this.selector = selector;
        this.socketChannel = socketChannel;
        input = ByteBuffer.allocate(serverConfig.getInputBufferSize());
        output = ByteBuffer.allocate(serverConfig.getOutputBufferSize());
        socketState = SocketState.READING;

        this.socketChannel.configureBlocking(false);
        this.selectionKey = socketChannel.register(selector, 0);
        this.selectionKey.attach(this);

        selectionKey.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    @Override
    public void run() {
        try {
            if (socketState == SocketState.READING) {
                read();
            }
            else if (socketState == SocketState.WRITING) {
                send();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void read() throws IOException {
        socketChannel.read(input);
        if (inputIsComplete()) {
            process();
            socketState = SocketState.WRITING;
            selectionKey.interestOps(SelectionKey.OP_WRITE);
        }
    }

    private void process() {
    }

    private boolean inputIsComplete() {
        return false;
    }

    private void send() throws IOException {
        socketChannel.write(output);
        if (outputIsComplete()) {
            selectionKey.cancel();
        }

    }

    private boolean outputIsComplete() {
        return false;
    }
}
