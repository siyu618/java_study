package com.study.examples;

/**
 * Created by tianyuzhi on 17/6/20.
 */
public class ClassLoadTest {
    static
    {
        i=0;
       // System.out.println(i);//这句编译器会报错：Cannot reference a field before it is defined（非法向前应用）
    }
    static int i=1;
}
