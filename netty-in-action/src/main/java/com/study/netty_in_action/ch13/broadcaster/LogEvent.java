package com.study.netty_in_action.ch13.broadcaster;

import lombok.Getter;
import lombok.ToString;

import java.net.InetSocketAddress;

/**
 * Created by tianyuzhi on 18/7/28.
 */
@Getter
@ToString
public  final  class LogEvent {
    public static final byte SEPARATOR = (byte)':';


    private final InetSocketAddress source;
    private final String logfile;
    private final String msg;
    private final long received;

    public LogEvent(String logfile, String msg) {
        this(null, -1, logfile, msg);
    }

    public LogEvent(InetSocketAddress source, long received, String logfile , String msg) {
        this.source = source;
        this.logfile = logfile;
        this.received = received;
        this.msg = msg;
    }
}
