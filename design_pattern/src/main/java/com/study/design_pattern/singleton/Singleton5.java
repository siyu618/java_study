package com.study.design_pattern.singleton;

/**
 * Created by tianyuzhi on 18/3/6.
 */
public class Singleton5 {
    private Singleton5() {}
    public static Singleton5 getInstance() {
        return NestedClass.instance;
    }


    private static class NestedClass {
        private static Singleton5 instance = new Singleton5();
    }

}


