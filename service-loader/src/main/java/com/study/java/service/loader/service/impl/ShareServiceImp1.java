package com.study.java.service.loader.service.impl;

import com.study.java.service.loader.service.IShareService;

public class ShareServiceImp1 implements IShareService {
    @Override
    public String sayHello() {
        return "shareService1SaysHello";
    }

    @Override
    public String getScheme() {
        return "shareService1GetsScheme";
    }

//    @Override
//    public Class getDefineClass() {
//        return this.getClass();
//    }
}
