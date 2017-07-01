package com.study.annotations.req;

import lombok.extern.log4j.Log4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/**
 * Created by tianyuzhi on 17/5/8.
 */
@Log4j
public class Test {

    private void testApi() {
        IReqApi api = create(IReqApi.class);
        api.login("whoami", "123456");

    }

    private <T> T create(final Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service}, new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                ReqType reqType = method.getAnnotation(ReqType.class);
                System.out.println("IReqApi---reqType->" + reqType);
                ReqUrl reqUrl = method.getAnnotation(ReqUrl.class);
                System.out.println("IReqApi---reqUrl->" + reqUrl);
                Type[] paramTypes = method.getParameterTypes();
                Annotation[][] paramAnnotationArray = method.getParameterAnnotations();
                int i = 0;
                for (Annotation[] paramAnnotations : paramAnnotationArray) {
                    if (null != paramAnnotations) {
                        ReqParam reqParam = (ReqParam)paramAnnotations[0];
                        System.out.println("reqParam---reqParam->" + reqParam.value() + "==" + args[i]);
                    }
                    i ++;
                }
                String result = "dummy";
                return result;
            }
        });
    }

    public static void main(String[] args) {
        new Test().testApi();
    }
}
