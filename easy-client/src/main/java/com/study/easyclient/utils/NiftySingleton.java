package com.study.easyclient.utils;

import com.facebook.nifty.client.NiftyClient;

/**
 * @author jixu
 */
public class NiftySingleton {
    private static volatile NiftyClient niftyClient = null;

    public static void register(NiftyClient client) {
        niftyClient = client;
    }

    /**
     * this method may return null
     */
    public static NiftyClient get() {
        return niftyClient;
    }
}
