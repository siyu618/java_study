package com.study.netty_in_action.ch11;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * Created by tianyuzhi on 18/7/28.
 */
public class LineBasedHandlerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(new LineBasedFrameDecoder(64 * 1024)).addLast(new Framehandler());
    }

    private static class Framehandler extends SimpleChannelInboundHandler<ByteBuf> {
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {

        }
    }
}
