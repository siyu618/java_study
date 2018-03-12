package com.study.design_pattern.proxy.vehical;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by tianyuzhi on 18/2/28.
 */
public class VehicalProxy {
    private IVehical vehical = null;

    public VehicalProxy(IVehical vehical) {
        this.vehical = vehical;
    }

    public IVehical getProxy() {
        return (IVehical) Proxy.newProxyInstance(VehicalProxy.class.getClassLoader(),
                new Class[]{IVehical.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("---before running");
                        Object ret = method.invoke(vehical, args);
                        System.out.println("---after running");
                        return ret;
                    }
                });
    }

    public static void main(String[] args) {
        IVehical vehical = new Car();
        IVehical proxy = new VehicalProxy(vehical).getProxy();
        proxy.run();
    }
}
