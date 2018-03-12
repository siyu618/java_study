package com.study.design_pattern.proxy.cglib;


import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by tianyuzhi on 18/3/1.
 */
public class ProxyFactory implements MethodInterceptor {
    private Object target;
    public ProxyFactory(Object object) {
        this.target = object;
    }

    public Object getProxyInstance() {
        Enhancer en = new Enhancer();
        en.setSuperclass(target.getClass());
        en.setCallback(this);
        return en.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("start tx2");
        Object retVal = method.invoke(target, args);
        System.out.println("commit tx2");
        return null;
    }

    public static void main(String[] args) {
        UserDao target = new UserDao();
        UserDao proxy = (UserDao)new ProxyFactory(target).getProxyInstance();
        proxy.save();
    }
}
