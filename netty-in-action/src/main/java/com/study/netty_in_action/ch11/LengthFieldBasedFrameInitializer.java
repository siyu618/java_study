package com.study.netty_in_action.ch11;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

/**
 * Created by tianyuzhi on 18/7/28.
 */
public class LengthFieldBasedFrameInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(
                new io.netty.handler.codec.LengthFieldBasedFrameDecoder(64 * 1024, 0, 8),
                new FrameHandler()
        );
    }

    public class FrameHandler extends SimpleChannelInboundHandler<ByteBuf> {
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {

        }
    }
}
