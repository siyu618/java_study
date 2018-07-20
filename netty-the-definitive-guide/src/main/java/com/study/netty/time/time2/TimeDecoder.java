package com.study.netty.time.time2;

import com.study.netty.time.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.List;

/**
 * Created by tianyuzhi on 18/7/20.
 */
public class TimeDecoder extends ByteToMessageCodec {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List list) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        list.add(new UnixTime(in.readUnsignedInt()));
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {

    }
}
