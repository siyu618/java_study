package com.study.java.service.loader.service.impl;

import com.study.java.service.loader.service.IShareService;

public class ShareServiceImp2 implements IShareService {
    @Override
    public String sayHello() {
        return "shareService2SaysHello";
    }



    @Override
    public String getScheme() {
        return "shareService2GetsScheme";
    }

//    @Override
//    public Class getDefineClass() {
//        return this.getClass();
//    }
}
