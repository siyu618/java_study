package com.study.easyclient.http;

import com.ning.http.client.ProxyServer;
import com.study.easyclient.cluster.Protocol;
import com.study.easyclient.core.InnerClient;
import com.study.easyclient.futureutils.FutureUtils;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author jixu
 */
public class HttpInnerClient extends InnerClient {

    private static Logger logger = LoggerFactory.getLogger(HttpInnerClient.class);

    private final AsyncHttpClient asyncHttpClient;
    private final long timeout;
    private final TimeUnit timeUnit;
    private final String urlPrefix;

    public HttpInnerClient(AsyncHttpClient asyncHttpClient, InetSocketAddress inetSocketAddress, long timeout, TimeUnit timeUnit){
        this(asyncHttpClient, inetSocketAddress, null, timeout, timeUnit);
    }

    public HttpInnerClient(AsyncHttpClient asyncHttpClient, InetSocketAddress inetSocketAddress, String protocol, long timeout, TimeUnit timeUnit) {
        super(inetSocketAddress);
        this.asyncHttpClient = asyncHttpClient;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        if (inetSocketAddress.getPort() == 0) {
            urlPrefix = "";
            logger.info("Zero port detected, setting urlPrefix to empty string");
        } else {
            if(Protocol.https.name().equals(protocol)) {
                this.urlPrefix = "https://" + inetSocketAddress.getHostName() + ":" + inetSocketAddress.getPort();
            }else{
                this.urlPrefix = "http://" + inetSocketAddress.getHostName() + ":" + inetSocketAddress.getPort();
            }
        }
    }

    public HttpResponse get(String uri) throws HttpException {
        return get(uri, null);
    }

    /**
     * GET method for http request
     * @param uri Example: /endpoint/get?param1=1&param2=2
     */
    public HttpResponse get(String uri, ProxyServer proxyServer) throws HttpException {
        String url = urlPrefix + uri;
        Future<Response> future = asyncHttpClient.prepareGet(url).setProxyServer(proxyServer).execute();
        try {
            Response response = future.get(timeout, timeUnit);
            return new HttpResponse(response.getResponseBody(), response.getStatusCode());
        } catch (Exception e) {
            String msg = "http get request failed: " + url;
            logger.error(msg, e);
            throw new HttpException(msg, e);
        }
    }

    public HttpResponse post(String uri, byte[] data) throws HttpException {
        return post(uri, data, null);
    }

    /**
     * POST method for http request
     */
    public HttpResponse post(String uri, byte[] data, ProxyServer proxyServer) throws HttpException {
        String url = urlPrefix + uri;
        Future<Response> future = asyncHttpClient.preparePost(url).setProxyServer(proxyServer).setBody(data).execute();
        try {
            Response response = future.get(timeout, timeUnit);
            return new HttpResponse(response.getResponseBody(), response.getStatusCode());
        } catch (Exception e) {
            String msg = "http post request failed: " + url;
            logger.error(msg, e);
            throw new HttpException(msg, e);
        }
    }

    public CompletableFuture<HttpResponse> getAsync(String uri) {
        return getAsync(uri, null);
    }

    /**
     * async GET method for http request
     */
    public CompletableFuture<HttpResponse> getAsync(String uri, ProxyServer proxyServer) {
        String url = urlPrefix + uri;
        CompletableFuture<HttpResponse> responseFuture = getResponseFuture(asyncHttpClient.prepareGet(url).setProxyServer(proxyServer));
        return FutureUtils.within(responseFuture, timeout, timeUnit);
    }

    public CompletableFuture<HttpResponse> postAsync(String uri, byte[] data) {
        return postAsync(uri, data, null);
    }

    /**
     * async POST method for http request
     */
    public CompletableFuture<HttpResponse> postAsync(String uri, byte[] data, ProxyServer proxyServer) {
        String url = urlPrefix + uri;
        CompletableFuture<HttpResponse> responseFuture = getResponseFuture(asyncHttpClient.preparePost(url).setProxyServer(proxyServer).setBody(data));
        return FutureUtils.within(responseFuture, timeout, timeUnit);
    }

    private CompletableFuture<HttpResponse> getResponseFuture(AsyncHttpClient.BoundRequestBuilder requestBuilder) {
        final CompletableFuture<HttpResponse> responseFuture = new CompletableFuture<>();
        requestBuilder.execute(new AsyncCompletionHandler<Response>() {
            @Override
            public Response onCompleted(Response response) {
                try {
                    responseFuture.complete(new HttpResponse(response.getResponseBody(), response.getStatusCode()));
                    return response;
                } catch (Exception e) {
                    responseFuture.completeExceptionally(e);
                    return response;
                }
            }

            @Override
            public void onThrowable(Throwable t) {
                responseFuture.completeExceptionally(t);
            }
        });
        return responseFuture;
    }

    public void close() throws IOException {}
}
