package com.study.design_pattern.strategy.car;

/**
 * Created by tianyuzhi on 18/3/8.
 */
public class NoLoadBehavior implements ILoadBehavior {
    @Override
    public void load() {
        System.out.println("不装载东西");
    }
}
