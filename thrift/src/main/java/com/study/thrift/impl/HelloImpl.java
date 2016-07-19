package com.study.thrift.impl;

import com.study.thrift.demo.Hello;
import org.apache.thrift.TException;

/**
 * Created by tianyuzhi on 16/7/19.
 */
public class HelloImpl implements Hello.Iface {


    @Override
    public String helloString(String para) throws TException {
        return para;
    }

    @Override
    public int helloInt(int para) throws TException {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return para;
    }

    @Override
    public boolean helloBoolean(boolean para) throws TException {
        return para;
    }

    @Override
    public void helloVoid() throws TException {
        System.out.println("Hello World");
    }

    @Override
    public String helloNull() throws TException {
        return null;
    }
}
