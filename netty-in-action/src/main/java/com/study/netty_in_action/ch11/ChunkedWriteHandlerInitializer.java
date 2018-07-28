package com.study.netty_in_action.ch11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.File;

/**
 * Created by tianyuzhi on 18/7/28.
 */
public class ChunkedWriteHandlerInitializer extends ChannelInitializer<Channel> {
    private final File file;
    private final SslContext sslCtx;

    public ChunkedWriteHandlerInitializer(File file, SslContext sslCtx) {
        this.file = file;
        this.sslCtx = sslCtx;
    }
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                .addLast(new SslHandler(sslCtx.newEngine(channel.alloc())))
                .addLast(new ChunkedWriteHandler())
                .addLast(new WriteStreamHandler());
    }

    public static class WriteStreamHandler extends ChannelInboundHandlerAdapter {

    }
}
