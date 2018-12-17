package com.study.reactor.reactor.multi;

import com.study.reactor.config.MultiReactorServerConfig;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiReactor implements Runnable {
    private Selector[] selectors;
    private int next = 0;
    private int selectorNum ;
    private MultiReactorServerConfig config;
    final ServerSocketChannel serverSocketChannel;
    final ExecutorService executorService;

    public MultiReactor(MultiReactorServerConfig serverConfig) throws IOException {
        this.config = serverConfig;

        executorService = Executors.newFixedThreadPool(config.getMultiThreadThreadPoolSize());

        this.selectorNum = serverConfig.getSelectorNum();
        this.selectors = new Selector[selectorNum];

        for (int i = 0; i < selectorNum; i ++) {
            selectors[i] = Selector.open();
        }

        this.serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(config.getServerPort()));

        SelectionKey sk = serverSocketChannel.register(selectors[next], SelectionKey.OP_ACCEPT);
        sk.attach(new MyAcceptor());
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                for (int i = 0; i < selectorNum; i ++) {
                    selectors[i].select();
                    Iterator<SelectionKey> iterator = selectors[i].selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        dispatch(selectionKey);
                        iterator.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dispatch(SelectionKey selectionKey) {
        Runnable runnable = (Runnable) selectionKey.attachment();
        if (Objects.nonNull(runnable)) {
            runnable.run();
        }
    }

    private class MyAcceptor implements Runnable {
        @Override
        public void run() {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (Objects.nonNull(socketChannel)) {
                    new MyHandler(selectors[next], socketChannel, config, executorService);
                    next = (next + 1) % selectorNum;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
