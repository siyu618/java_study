package com.study.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by tianyuzhi on 17/3/27.
 */
public class BClass {

    public void testPrivate() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        AClass obj = new AClass();
        Method method1 = AClass.class.getDeclaredMethod("getPrivate", null);
        method1.setAccessible(true);
        System.out.println(method1.invoke(obj, null));

        Method method2 = AClass.class.getDeclaredMethod("getPrivateWithParam", String.class);
        method2.setAccessible(true);
        System.out.println(method2.invoke(obj, "I am Param"));


        Method method3 = AClass.class.getMethod("getPublic", null);
        System.out.println(method3.invoke(obj, null));

        Method method4 = AClass.class.getMethod("getPublicWithParam", int.class);
        System.out.println(method4.invoke(obj, 3));



    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        new BClass().testPrivate();
        System.out.println();
    }
}
