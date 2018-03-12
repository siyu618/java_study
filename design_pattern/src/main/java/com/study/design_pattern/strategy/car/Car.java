package com.study.design_pattern.strategy.car;


/**
 * Created by tianyuzhi on 18/3/8.
 */
public abstract class Car {
    IDriveBehavior driveBehavior;
    ILoadBehavior loadBehavior;

    public abstract void play();

    public void load() {
        loadBehavior.load();
    }
    public void drive() {
        driveBehavior.driver();
    }

    public void setDriveBehavior(IDriveBehavior driveBehavior) {
        this.driveBehavior = driveBehavior;
    }
    public void setLoadBehavior(ILoadBehavior loadBehavior) {
        this.loadBehavior = loadBehavior;
    }
}
