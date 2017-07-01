package com.study.classloader;

/**
 * Created by tianyuzhi on 17/6/27.
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getContextClassLoader());;
        System.out.println(Test.class.getClassLoader());
        System.out.println(ClassLoader.getSystemClassLoader());
    }
}
