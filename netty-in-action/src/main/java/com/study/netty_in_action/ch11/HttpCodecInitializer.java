package com.study.netty_in_action.ch11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * Created by tianyuzhi on 18/7/28.
 */
public class HttpCodecInitializer extends ChannelInitializer<Channel> {
    private final SslContext context;
    private final boolean isClient;

    public HttpCodecInitializer(SslContext context, boolean isClient) {
        this.context = context;
        this.isClient = isClient;
    }
    @Override
    protected void initChannel(Channel channel) throws Exception {
        SSLEngine engine = context.newEngine(channel.alloc());
        channel.pipeline().addFirst("ssl", new SslHandler(engine, isClient));
        ChannelPipeline pipeline = channel.pipeline();

        if (isClient) {
            pipeline.addLast("codec", new HttpClientCodec());
        }
        else {
            pipeline.addLast("codec", new HttpServerCodec());
        }
    }
}
