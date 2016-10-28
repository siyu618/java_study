package com.study.easyclient.http;

import com.ning.http.client.ProxyServer;

import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.CompletableFuture;

/**
 * @author jixu
 */
@ThreadSafe
public interface HttpClient {

    HttpResponse get(String uri, String hashKey);

    HttpResponse get(String uri, String hashKey, ProxyServer proxyServer);

    default HttpResponse get(String uri) {
        return get(uri, null);
    }

    CompletableFuture<HttpResponse> getAsync(String uri, String hashKey);

    CompletableFuture<HttpResponse> getAsync(String uri, String hashKey, ProxyServer proxyServer);

    default CompletableFuture<HttpResponse> getAsync(String uri) {
        return getAsync(uri, null);
    }

    HttpResponse post(String uri, byte[] data, String hashKey);

    HttpResponse post(String uri, byte[] data, String hashKey, ProxyServer proxyServer);

    default HttpResponse post(String uri, byte[] data) {
        return post(uri, data, null);
    }

    CompletableFuture<HttpResponse> postAsync(String uri, byte[] data, String hashKey);

    CompletableFuture<HttpResponse> postAsync(String uri, byte[] data, String hashKey, ProxyServer proxyServer);

    default CompletableFuture<HttpResponse> postAsync(String uri, byte[] data) {
        return postAsync(uri, data, null);
    }

    HttpResponse apply(HttpRequest request);

    CompletableFuture<HttpResponse> applyAsync(HttpRequest request);
}
