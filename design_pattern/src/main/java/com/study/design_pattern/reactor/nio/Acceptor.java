package com.study.design_pattern.reactor.nio;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * Created by tianyuzhi on 18/4/20.
 */
public class Acceptor implements Runnable {
    private Reactor reactor;

    public Acceptor(Reactor reactor) {
        this.reactor = reactor;
    }

    @Override
    public void run() {
        try {
            SocketChannel socketChannel = reactor.serverSocketChannel.accept();
            if (null != socketChannel) {
                new SocketReadHandler(reactor.selector, socketChannel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
