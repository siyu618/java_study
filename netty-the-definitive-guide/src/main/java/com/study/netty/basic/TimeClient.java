//package com.study.netty.basic;
//
//import io.netty.bootstrap.Bootstrap;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelOption;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioSocketChannel;
//
//import java.net.InetAddress;
//
///**
// * Created by tianyuzhi on 18/7/10.
// */
//public class TimeClient {
//
//    public static void main(String[] args) throws InterruptedException {
//        int port = 10080;
//        new TimeClient().connect(port, "localhost");
//    }
//
//    private void connect(int port, String host) throws InterruptedException {
//        EventLoopGroup group = new NioEventLoopGroup();
//        try {
//            Bootstrap bootstrap = new Bootstrap();
//            bootstrap.group(group).channel(NioSocketChannel.class)
//                    .option(ChannelOption.TCP_NODELAY, true)
//                    .handler(new ChannelInitializer<SocketChannel>() {
//                        @Override
//                        protected void initChannel(SocketChannel socketChannel) throws Exception {
//                            socketChannel.pipeline().addLast(new TimeClientHandler());
//                        }
//                    });
//            ChannelFuture f = bootstrap.connect(host, port).sync();
//            f.channel().closeFuture().sync();
//        } finally {
//            group.shutdownGracefully();
//        }
//    }
//}
