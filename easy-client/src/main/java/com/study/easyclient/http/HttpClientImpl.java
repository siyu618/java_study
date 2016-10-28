package com.study.easyclient.http;

import com.ning.http.client.ProxyServer;
import com.study.easyclient.core.BaseClient;
import com.study.easyclient.cluster.Cluster;
import com.study.easyclient.exceptions.LoadBalancerException;
import com.study.easyclient.loadbalancer.LoadBalancer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

/**
 * @author jixu
 */
public class HttpClientImpl extends BaseClient<HttpInnerClient> implements HttpClient {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientImpl.class);

    public HttpClientImpl(Cluster cluster, LoadBalancer<HttpInnerClient> loadBalancer) {
        super(cluster, loadBalancer);
    }

    public HttpResponse get(String uri, String hashKey) {
        return get(uri, hashKey, null);
    }

    public HttpResponse get(String uri, String hashKey, ProxyServer proxyServer) {
        HttpInnerClient client = null;
        try {
            client = loadBalancer.getClient(hashKey);
            HttpResponse response = client.get(uri, proxyServer);
            loadBalancer.withDraw(client, true);
            return response;
        } catch (LoadBalancerException e) {
            logger.error("LoadBalancer getClient failed", e);
            return null;
        } catch (HttpException e) {
            String url = client.getAddress().toString() + uri;
            logger.error("Client http failed with request: " + url, e);
            loadBalancer.withDraw(client, false);
            return null;
        }
    }

    public CompletableFuture<HttpResponse> getAsync(String uri, String hashKey) {
        return getAsync(uri, hashKey, null);
    }

    public CompletableFuture<HttpResponse> getAsync(String uri, String hashKey, ProxyServer proxyServer) {
        try {
            HttpInnerClient client = loadBalancer.getClient(hashKey);
            CompletableFuture<HttpResponse> result = client.getAsync(uri, proxyServer);
            result.exceptionally(throwable -> {
                String url = client.getAddress().toString() + uri;
                logger.error("Client http failed with request: " + url, throwable);
                return null;
            }).thenAccept(httpResponse -> loadBalancer.withDraw(client, httpResponse != null));
            return result;
        } catch (LoadBalancerException e) {
            logger.error("LoadBalancer getClient failed", e);
            return null;
        }
    }

    public HttpResponse post(String uri, byte[] data, String hashKey) {
        return post(uri, data, hashKey, null);
    }

    public HttpResponse post(String uri, byte[] data, String hashKey, ProxyServer proxyServer) {
        HttpInnerClient client = null;
        try {
            client = loadBalancer.getClient(hashKey);
            HttpResponse response = client.post(uri, data, proxyServer);
            loadBalancer.withDraw(client, true);
            return response;
        } catch (LoadBalancerException e) {
            logger.error("LoadBalancer getClient failed", e);
            return null;
        } catch (HttpException e) {
            String url = client.getAddress().toString() + uri;
            logger.error("Client http failed with request: " + url, e);
            loadBalancer.withDraw(client, false);
            return null;
        }
    }

    public CompletableFuture<HttpResponse> postAsync(String uri, byte[] data, String hashKey) {
        return postAsync(uri, data, hashKey, null);
    }

    public CompletableFuture<HttpResponse> postAsync(String uri, byte[] data, String hashKey, ProxyServer proxyServer) {
        try {
            HttpInnerClient client = loadBalancer.getClient(hashKey);
            CompletableFuture<HttpResponse> result = client.postAsync(uri, data, proxyServer);
            result.exceptionally(throwable -> {
                String url = client.getAddress().toString() + uri;
                logger.error("Client http failed with request: " + url, throwable);
                return null;
            }).thenAccept(httpResponse -> loadBalancer.withDraw(client, httpResponse != null));
            return result;
        } catch (LoadBalancerException e) {
            logger.error("LoadBalancer getClient failed", e);
            return null;
        }
    }

    public HttpResponse apply(HttpRequest httpRequest) {
        if (httpRequest.method.equals(HttpMethod.GET)) {
            return get(httpRequest.uri, httpRequest.hashKey, httpRequest.proxyServer);
        } else {
            return post(httpRequest.uri, httpRequest.data, httpRequest.hashKey, httpRequest.proxyServer);
        }
    }

    public CompletableFuture<HttpResponse> applyAsync(HttpRequest httpRequest) {
        if (httpRequest.method.equals(HttpMethod.GET)) {
            return getAsync(httpRequest.uri, httpRequest.hashKey, httpRequest.proxyServer);
        } else {
            return postAsync(httpRequest.uri, httpRequest.data, httpRequest.hashKey, httpRequest.proxyServer);
        }
    }
}
