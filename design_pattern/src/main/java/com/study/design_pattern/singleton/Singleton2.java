package com.study.design_pattern.singleton;

/**
 * Created by tianyuzhi on 18/3/6.
 */
public class Singleton2 {
    private static Singleton2 instance = null;
    private Singleton2() {}
    public static Singleton2 getInstance() {
        if (null == instance) {
            instance = new Singleton2();
        }
        return instance;
    }
}
