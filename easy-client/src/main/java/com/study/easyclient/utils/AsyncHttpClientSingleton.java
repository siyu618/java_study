package com.study.easyclient.utils;

import com.ning.http.client.AsyncHttpClient;

/**
 * @author jixu
 */
public class AsyncHttpClientSingleton {
    private static volatile AsyncHttpClient asyncHttpClient = null;

    public static void register(AsyncHttpClient client) {
        asyncHttpClient = client;
    }

    /**
     * this method may return null
     */
    public static AsyncHttpClient get() {
        return asyncHttpClient;
    }
}
