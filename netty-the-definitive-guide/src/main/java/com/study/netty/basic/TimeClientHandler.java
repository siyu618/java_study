//package com.study.netty.basic;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelHandler;
//import io.netty.channel.ChannelHandlerAdapter;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelHandlerInvoker;
//import io.netty.util.concurrent.EventExecutorGroup;
//import sun.nio.cs.StandardCharsets;
//
///**
// * Created by tianyuzhi on 18/7/10.
// */
//public class TimeClientHandler extends ChannelHandlerAdapter {
//    private final ByteBuf firstMessage;
//
//    public TimeClientHandler(){
//        byte[] req = "QUERY TIME ORDER".getBytes();
//        firstMessage = Unpooled.buffer(req.length);
//        firstMessage.writeBytes(req);
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        ctx.close();
//    }
//
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush(firstMessage);
//    }
//
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf = (ByteBuf)msg;
//        byte[] req = new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        String body = new String(req, java.nio.charset.StandardCharsets.UTF_8);
//        System.out.println("Now is : " + body);
//    }
//
//
//}
