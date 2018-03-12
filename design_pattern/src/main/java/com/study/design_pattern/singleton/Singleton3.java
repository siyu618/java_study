package com.study.design_pattern.singleton;

/**
 * Created by tianyuzhi on 18/3/6.
 */
public class Singleton3 {
    private static Singleton3 instance = null;
    private Singleton3() {}
    public static synchronized Singleton3 getInstance() {
        if (null == instance) {
            instance = new Singleton3();
        }
        return instance;
    }
}
