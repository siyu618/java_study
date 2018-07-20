package com.study.netty.time.time2;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;


/**
 * Created by tianyuzhi on 18/7/20.
 */
@ChannelHandler.Sharable
public class NettyTimeIntializer2 extends ChannelInitializer<io.netty.channel.socket.SocketChannel> {

    @Override
    protected void initChannel(io.netty.channel.socket.SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new TimeEncoder()).addLast(new TimeServerHander2());
    }
}
