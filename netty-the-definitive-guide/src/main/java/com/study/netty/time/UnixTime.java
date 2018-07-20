package com.study.netty.time;

import java.util.Date;

/**
 * Created by tianyuzhi on 18/7/20.
 */
public class UnixTime {
    private final long value;
    public UnixTime() {
        this(System.currentTimeMillis()/1000L +  2208988800L);
    }

    public UnixTime(long l) {
        this.value = l;
    }

    public long value() {
        return value;
    }

    @Override
    public String toString() {
        return new Date((value() - 2208988800L) * 1000).toString();
    }
}
