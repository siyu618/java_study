package com.study.design_pattern.proxy.vehical;

/**
 * Created by tianyuzhi on 18/2/28.
 */
public class Car implements IVehical {
    @Override
    public void run() {
        System.out.println("Cur running");
    }
}
