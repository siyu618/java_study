package com.study.easyclient.http;

import com.ning.http.client.AsyncHttpClient;
import com.study.easyclient.core.InnerClient;
import com.study.easyclient.core.InnerClientFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author jixu
 */
public class HttpInnerClientFactory implements InnerClientFactory {
    private final AsyncHttpClient asyncHttpClient;
    private final long timeout;
    private final TimeUnit timeUnit;
    private final String protocol;

    public HttpInnerClientFactory(AsyncHttpClient asyncHttpClient, long timeout, TimeUnit timeUnit) {
       this(asyncHttpClient, null, timeout,timeUnit);
    }

    public HttpInnerClientFactory(AsyncHttpClient asyncHttpClient, String protocol, long timeout, TimeUnit timeUnit) {
        this.asyncHttpClient = asyncHttpClient;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.protocol = protocol;
    }

    public InnerClient getInnerClient(InetSocketAddress inetSocketAddress) {
        return new HttpInnerClient(asyncHttpClient, inetSocketAddress, protocol, timeout, timeUnit);
    }
}
