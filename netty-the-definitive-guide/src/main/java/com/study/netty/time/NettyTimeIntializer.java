package com.study.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

/**
 * Created by tianyuzhi on 18/7/20.
 */
@ChannelHandler.Sharable
public class NettyTimeIntializer extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        final ByteBuf time = ctx.alloc().buffer(4);
        time.writeInt((int)(System.currentTimeMillis()/1000L + 2208988800L));
        final ChannelFuture f = ctx.writeAndFlush(time);

        f.addListener((ChannelFutureListener) channelFuture -> {
            assert  f == channelFuture;
            ctx.close();
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
