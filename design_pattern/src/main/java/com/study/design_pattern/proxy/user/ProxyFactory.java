package com.study.design_pattern.proxy.user;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by tianyuzhi on 18/3/1.
 */
public class ProxyFactory {
    private Object target;
    public ProxyFactory(Object obj) {
        this.target = obj;
    }
    public Object getProxyInstance() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("start tx");
                        Object retVal = method.invoke(target, args);
                        System.out.println("commit tx");
                        return retVal;
                    }
                });
    }

    public static void main(String[] args) {
        IUserDao userDao = new UserDao();
        IUserDao proxy = (IUserDao)(new ProxyFactory(userDao).getProxyInstance());
        proxy.save();

    }

}
