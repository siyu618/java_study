package com.study.java.service.loader.test;

import com.study.java.service.loader.service.IShareService;

import java.util.ServiceLoader;

public class TestServiceLoader {
    public static void main(String...args) {
        ServiceLoader<IShareService> serviceLoader = ServiceLoader
                .load(IShareService.class);
        for (IShareService service : serviceLoader) {
            System.out.println(service.getScheme());
            System.out.println(service.sayHello());
            System.out.println(service.getDefineClass().getCanonicalName());
            System.out.println();
        }
    }
}
