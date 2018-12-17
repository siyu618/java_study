package com.study.reactor.reactor.single;

import com.study.reactor.config.ServerConfig;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author didi
 */
public class SingleReactor implements Runnable {

    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;
    private ServerConfig serverConfig;
    public SingleReactor(ServerConfig serverConfig) throws IOException {
        this.serverConfig = serverConfig;
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(serverConfig.getServerPort()));
        serverSocketChannel.configureBlocking(false);
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        selectionKey.attach(new Acceptor());
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    dispatch(iterator.next());
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void dispatch(SelectionKey selectionKey) {
        Runnable runnable = (Runnable) selectionKey;
        if (Objects.nonNull(runnable)) {
            runnable.run();
        }
    }

    private class Acceptor implements  Runnable {
        @Override
        public void run() {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (Objects.nonNull(socketChannel)) {
                    new MyHandler(selector, socketChannel, serverConfig);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
