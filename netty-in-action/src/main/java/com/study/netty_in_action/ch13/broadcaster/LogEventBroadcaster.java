package com.study.netty_in_action.ch13.broadcaster;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;

/**
 * Created by tianyuzhi on 18/7/28.
 */
public class LogEventBroadcaster {
    private final EventLoopGroup group;
    private final Bootstrap bootstrap;
    private final File file;

    public LogEventBroadcaster(InetSocketAddress address, File file) {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new LogEventEncoder(address));
        this.file = file;
    }

    public void run() throws Exception {
        Channel channel = bootstrap.bind(0).sync().channel();
        long pointer = 0;
        for (;;) {
            long len = file.length();
            if (len < pointer) {
                pointer = len;
            }
            else {
                RandomAccessFile raf = new RandomAccessFile(file, "r");
                raf.seek(pointer);
                String line ;
                while ((line = raf.readLine()) != null) {
                    channel.writeAndFlush(new LogEvent(null, -1, file.getAbsolutePath(), line));
                }
                pointer = raf.getFilePointer();
                raf.close();

            }
            try {
                Thread.sleep(1000);
            }
             catch (InterruptedException e) {
                Thread.interrupted();
                break;
             }
        }

    }

    public void stop() {
        group.shutdownGracefully();
    }

    public static void main(String...args) throws Exception {
        String file = "index.html";
        int port = 9999;
        LogEventBroadcaster logEventBroadcaster =
                new LogEventBroadcaster(new InetSocketAddress("255.255.255.255", port), new File(file));
        try {
            logEventBroadcaster.run();;
        } finally {
            logEventBroadcaster.stop();;
        }
    }
}
