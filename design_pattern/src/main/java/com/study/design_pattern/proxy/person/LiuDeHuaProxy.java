package com.study.design_pattern.proxy.person;

import com.study.design_pattern.GsonFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author tianyuzhi
 * @date 18/2/28
 */
public class LiuDeHuaProxy {

    private IPerson person = new LiuDeHua();

    public IPerson getProxy() {
        return (IPerson) Proxy.newProxyInstance(LiuDeHuaProxy.class.getClassLoader(),
                person.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if ("sing".equals(method.getName())) {
                            System.out.println("我是他的经纪人，要唱歌先给10w");
                            return method.invoke(person, args);
                        } else if ("dance".equals(method.getName())) {
                            System.out.println("我是他的经纪人，要跳舞先给20w");
                            return method.invoke(person, args);
                        }
                        return null;
                    }
                });
    }

    public static void main(String[] args) {
        LiuDeHuaProxy liuDeHuaProxy = new LiuDeHuaProxy();
        IPerson proxy = liuDeHuaProxy.getProxy();
        String retVal = proxy.sing("冰雨");
        System.out.println(retVal);
        retVal = proxy.dance("江南Style");
        System.out.println(retVal);

        for (Method method : liuDeHuaProxy.getClass().getMethods()) {
            System.out.println(GsonFactory.getDefaultGson().toJson(method.getName()));
        }
        System.out.println("===");
        for (Method method : proxy.getClass().getMethods()) {
            System.out.println(GsonFactory.getDefaultGson().toJson(method.getName()));
        }
    }
}
