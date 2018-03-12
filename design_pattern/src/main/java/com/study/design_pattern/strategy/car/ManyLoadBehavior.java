package com.study.design_pattern.strategy.car;

/**
 * Created by tianyuzhi on 18/3/8.
 */
public class ManyLoadBehavior implements ILoadBehavior {
    @Override
    public void load() {
        System.out.println("装载大量东西");
    }
}
