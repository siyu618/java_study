//package com.study.netty.basic;
//
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.channel.*;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//
//
///**
// * Created by tianyuzhi on 18/7/10.
// */
//public class TimeServer {
//    public static void main(String[] args) throws InterruptedException {
//        int port = 10080;
//        new TimeServer().bind(port);
//    }
//
//    private void bind(int port) throws InterruptedException {
//        EventLoopGroup boosGroup = new NioEventLoopGroup();
//        EventLoopGroup workerGroup = new NioEventLoopGroup();
//
//        try {
//            ServerBootstrap b = new ServerBootstrap();
//            b.group(boosGroup, workerGroup)
//                    .channel(NioServerSocketChannel.class)
//                    .option(ChannelOption.SO_BACKLOG, 1024)
//                    .childHandler(new ChildChannelHandler());
//            ChannelFuture future = b.bind(port).sync();
//            future.channel().closeFuture().sync();
//        } finally {
//            boosGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
//        }
//    }
//
//    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
//        @Override
//        protected void initChannel(SocketChannel socketChannel) throws Exception {
//            socketChannel.pipeline().addLast(new TimeServerHandler());
//        }
//    }
//}
