package com.study.design_pattern.singleton;

/**
 * Created by tianyuzhi on 18/3/6.
 */
public class Singleton4 {
    private static volatile Singleton4 instance = null;
    private Singleton4() {}
    public static Singleton4 getInstance() {
        if (null == instance) {
            synchronized (Singleton4.class) {
                if (null != instance) {
                    Singleton4 t = new Singleton4();
                    instance = t;
                }
            }
        }
        return instance;
    }
}
