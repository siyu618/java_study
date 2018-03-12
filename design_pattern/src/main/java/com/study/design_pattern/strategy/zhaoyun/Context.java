package com.study.design_pattern.strategy.zhaoyun;

/**
 * Created by tianyuzhi on 18/3/8.
 */
public class Context {
    private IStrategy strategy;
    public Context(IStrategy strategy) {
        this.strategy = strategy;
    }

    public void operate() {
        this.strategy.operate();
    }
}
