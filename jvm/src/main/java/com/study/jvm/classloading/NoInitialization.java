package com.study.jvm.classloading;

/**
 * Created by tianyuzhi on 16/5/28.
 */

class Super {
    static {
        System.out.println("Supper class init");
    }
    public static int value = 123;
}

class Sub extends Super {
    static {
        System.out.println("SubClass init");
    }

}

public class NoInitialization {
    public static void main(String[] args) {
        System.out.println(Sub.value);
    }
}
