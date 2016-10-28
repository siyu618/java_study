package com.study.easyclient.http;

import com.ning.http.client.ProxyServer;

/**
 * @author jixu
 */
public class HttpRequest {
    public final HttpMethod method;
    public final String uri;
    public final byte[] data;
    public final String hashKey;
    public final ProxyServer proxyServer;

    public HttpRequest(HttpMethod method, String uri, byte[] data, String hashKey, ProxyServer proxyServer) {
        this.method = method;
        this.uri = uri;
        this.data = data;
        this.hashKey = hashKey;
        this.proxyServer = proxyServer;
    }

    public HttpRequest(HttpMethod method, String uri, byte[] data, String hashKey) {
      this(method, uri, data, hashKey, null);
    }

    public HttpRequest(HttpMethod method, String uri, ProxyServer proxyServer) {
        this(method, uri, null, null, proxyServer);
    }

    public HttpRequest(HttpMethod method, String uri) {
        this(method, uri, null, null, null);
    }

    public HttpRequest(HttpMethod method, String uri, String hashKey) {
        this(method, uri, null, hashKey, null);
    }

    @Override
    public String toString() {
        return "HttpRequest(" + method + "," + uri + ")";
    }
}
