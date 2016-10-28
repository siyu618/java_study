package com.study.easyclient.utils;

import org.apache.curator.framework.CuratorFramework;

/**
 * @author jixu
 */
public class CuratorSingleton {
    private static volatile CuratorFramework curatorFramework = null;

    public static void register(CuratorFramework cf) {
        curatorFramework = cf;
    }

    /**
     * this method may return null
     */
    public static CuratorFramework get() {
        return curatorFramework;
    }
}
