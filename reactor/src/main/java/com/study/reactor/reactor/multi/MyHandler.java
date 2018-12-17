package com.study.reactor.reactor.multi;

import com.study.reactor.config.MultiReactorServerConfig;
import com.study.reactor.config.ServerConfig;
import com.study.reactor.config.SocketState;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;

public class MyHandler implements Runnable {
    private final SocketChannel socketChannel;
    private final Selector selector;
    private final SelectionKey selectionKey;

    private ExecutorService executorService;
    private ByteBuffer input;
    private ByteBuffer output;
    private SocketState socketState;
    public MyHandler(Selector selector, SocketChannel socketChannel, MultiReactorServerConfig serverConfig, ExecutorService executorService) throws IOException {
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
            socketState = SocketState.PROCESSING;
            executorService.submit(new MyProcessor());
        }
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

    private class MyProcessor implements Runnable {

        @Override
        public void run() {
            processAndHandOff();
        }
    }

    private void process() {

    }
    private void processAndHandOff() {
        process();
        socketState = SocketState.WRITING;
        selectionKey.interestOps(SelectionKey.OP_WRITE);
    }
}
