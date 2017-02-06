package com.study.nio;

import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by tianyuzhi on 17/1/19.
 */
public class SelectorTest {
    public void test() throws IOException {
        Selector selector = Selector.open();
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);

        SelectionKey key1 = channel.register(selector, SelectionKey.OP_READ);

        while (true) {
            int readyChannels = selector.select();
            if (readyChannels == 0) continue;

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    //
                    Channel channel1 = key.channel();
                }
                else if (key.isConnectable()) {

                }
                else if (key.isReadable()) {

                }
                else if (key.isWritable()) {

                }
                iterator.remove();
            }
        }

    }
    public static void main(String[] args) {
    }
}
