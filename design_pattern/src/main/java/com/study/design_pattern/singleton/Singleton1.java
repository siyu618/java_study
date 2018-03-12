package com.study.design_pattern.singleton;

/**
 * Created by tianyuzhi on 18/3/6.
 */
public class Singleton1 {

    private static Singleton1 instance = new Singleton1();
    private Singleton1() {}

    public static Singleton1 getInstance() {
        return instance;
    }
}
