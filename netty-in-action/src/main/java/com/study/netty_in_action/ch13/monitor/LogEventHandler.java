package com.study.netty_in_action.ch13.monitor;

import com.study.netty_in_action.ch13.broadcaster.LogEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by tianyuzhi on 18/7/28.
 */
public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LogEvent logEvent) throws Exception {
        System.out.println(logEvent);
    }
}
