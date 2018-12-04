package com.study.java.service.loader.service;

public interface IShareService {
    String sayHello();
    String getScheme();
    default Class getDefineClass() {return this.getClass(); };
}
