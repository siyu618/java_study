package com.study.netty.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by tianyuzhi on 18/7/20.
 */
public class NettyEchoServer {
    private static final int PORT = 8888;
    private ServerBootstrap serverBootstrap;

    private EventLoopGroup boosGroup = new NioEventLoopGroup(1);
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    private void open() throws InterruptedException {
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boosGroup, workerGroup)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new NettyEchoIntializer());
        Channel ch = serverBootstrap.bind(PORT).sync().channel();
        ch.closeFuture().sync();
    }

    public void close() {
        boosGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public static void main(String...args) {
        NettyEchoServer nettyTelnetServer = new NettyEchoServer();
        try {
            nettyTelnetServer.open();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            nettyTelnetServer.close();
        }
    }
}

